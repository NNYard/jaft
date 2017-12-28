package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.entity.GetKvResponse;
import com.ericsson.rda.jaft.entity.Response;
import com.ericsson.rda.jaft.entity.ResponseType;
import com.ericsson.rda.jaft.entity.SetKvResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Eric Cen on 8/25/2017.
 */
public class KeyValueHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyValueHandler.class);
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String data;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }
    public ChannelPromise sendMessage(Object message) {
        if (ctx == null)
            throw new IllegalStateException();
        promise = ctx.writeAndFlush(message).channel().newPromise();
        return promise;
    }

    public String getData() {
        return data;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response = (Response) msg;
        ResponseType responseType = response.getResponseType();
        LOGGER.info("Receive KeyValue message of : "  + responseType);
        if(responseType == ResponseType.GET_KEY_VALUE){
            data = ((GetKvResponse)response).getValue();
            promise.setSuccess();
        } else if(responseType == ResponseType.SET_KEY_VALUE){
            data = ((SetKvResponse)response).getLeaderId();
            promise.setSuccess();
        }
    }
}
