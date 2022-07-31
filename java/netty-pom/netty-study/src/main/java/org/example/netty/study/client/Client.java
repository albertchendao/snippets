package org.example.netty.study.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;
import org.example.netty.study.client.codec.*;
import org.example.netty.study.client.dispatcher.OperationResultFuture;
import org.example.netty.study.client.dispatcher.RequestPendingCenter;
import org.example.netty.study.client.dispatcher.ResponseDispatcherHandler;
import org.example.netty.study.common.OperationResult;
import org.example.netty.study.common.RequestMessage;
import org.example.netty.study.common.order.OrderOperation;
import org.example.netty.study.common.util.IdUtil;

public class Client {

    @SneakyThrows
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);

        RequestPendingCenter center = new RequestPendingCenter();

        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());

                pipeline.addLast(new ResponseDispatcherHandler(center));
                pipeline.addLast(new OperationToRequestMessageEncoder());

                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();

        long streamId = IdUtil.nextId();
        RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(1001, "todo"));
        OperationResultFuture future = new OperationResultFuture();
        center.add(streamId, future);

        channelFuture.channel().writeAndFlush(requestMessage);
        OperationResult result = future.get();
        System.out.println(result);

//        OrderOperation operation = new OrderOperation(2001, "todo");
//        channelFuture.channel().writeAndFlush(operation);


        channelFuture.channel().closeFuture().sync();
    }
}
