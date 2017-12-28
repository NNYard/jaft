package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.entity.Response;
import com.ericsson.rda.jaft.processor.ResponseProcessor;
import com.ericsson.rda.jaft.processor.ResponseProcessorFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by exiango on 8/24/2017.
 */
public class JaftClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response = (Response) msg; // (1)
        ResponseProcessor processor = ResponseProcessorFactory.getResponseProcessor(response.getResponseType());
        processor.process(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        try {
            ctx.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}
