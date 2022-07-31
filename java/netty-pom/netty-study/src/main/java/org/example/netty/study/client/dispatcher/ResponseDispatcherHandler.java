package org.example.netty.study.client.dispatcher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty.study.common.ResponseMessage;

public class ResponseDispatcherHandler extends SimpleChannelInboundHandler<ResponseMessage> {

    private RequestPendingCenter center;

    public ResponseDispatcherHandler(RequestPendingCenter center) {
        this.center = center;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage) throws Exception {
        center.set(responseMessage.getMessageHeader().getStreamId(), responseMessage.getMessageBody());
    }
}
