package org.example.netty.study.client.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.example.netty.study.common.Operation;
import org.example.netty.study.common.RequestMessage;
import org.example.netty.study.common.util.IdUtil;

import java.util.List;

public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Operation operation, List<Object> list) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), operation);
        list.add(requestMessage);
    }
}
