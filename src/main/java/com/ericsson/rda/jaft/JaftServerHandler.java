package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.entity.Request;
import com.ericsson.rda.jaft.entity.RequestType;
import com.ericsson.rda.jaft.entity.Response;
import com.ericsson.rda.jaft.processor.ProcessorFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Eric Cen on 8/24/2017.
 */
public class JaftServerHandler extends SimpleChannelInboundHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaftServerHandler.class);

    private JaftNode node;

    public JaftServerHandler(JaftNode node) {
        this.node = node;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request)msg;
        RequestType requestType = request.getType();
        LOGGER.info("Receive request of: " + requestType.name());
        Response response = ProcessorFactory.getProcessor(requestType)
                                            .process(request, node);
        ctx.write(response);
        ctx.flush();
    }
}
