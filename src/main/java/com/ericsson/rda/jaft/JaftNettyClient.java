package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.entity.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by exiango on 8/24/2017.
 */
public class JaftNettyClient {

    private static final Logger logger = LoggerFactory.getLogger(JaftNettyClient.class);

    private String host;
    private int port;
    private ChannelFuture channelFuture;
    private Channel channel;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();


    public JaftNettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void start() throws InterruptedException {
        logger.info("Starting netty client...");
        logger.info("Server address: {}, port: {}", host, port);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast("Encoder", new ObjectEncoder());
                pipeline.addLast("Handler", new JaftClientHandler());
            }
        });

        channelFuture = bootstrap.connect(host, port).sync();
        channel = channelFuture.channel();
        logger.info("Netty client started...");
    }

    public void sendRequest(Request request) {
        logger.info("Send request, request: {}", request.toString());
        channel.write(request);
        channel.flush();
    }

    public void stop() throws InterruptedException {
        try {
            if (channelFuture != null)
                channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
