package com.ericsson.rda.jaft;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * Created by Eric Cen on 8/24/2017.
 */
public class JaftNettyServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaftNettyServer.class);
    private int port;

    private JaftNode node;

    public JaftNettyServer(int port) {
        this.port = port;
        this.node = JaftContext.getNode();

    }

    public void run() throws InterruptedException {
        LOGGER.info("Starting Jaft Netty Server.....");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("Decoder", new ObjectDecoder(ClassResolvers.
                                    cacheDisabled(this.getClass().getClassLoader()
                                    )));
                            pipeline.addLast("Encoder", new ObjectEncoder());
                            pipeline.addLast("Handler", new JaftServerHandler(node));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static void startClients(int port) throws InterruptedException {
        Map<String, JaftNettyClient> clients = JaftContext.getClients();
        long interval = 30000;
        for (String host : JaftContext.getPeers()) {
            boolean clientStarted = false;
            long currentTime = System.currentTimeMillis();
            long timeout = currentTime + interval;
            do {
                currentTime = System.currentTimeMillis();
                JaftNettyClient jaftClient = new JaftNettyClient(host, port);
                try {
                    jaftClient.start();
                    clients.put(host, jaftClient);
                    clientStarted = true;
                } catch (Exception e) {
                    LOGGER.info("Failed connect to server: {}, going to try again.", host);
                    jaftClient.stop();
                    clientStarted = false;
                }
                Thread.sleep(1000);
            } while (!clientStarted && timeout > currentTime);
        }
        JaftContext.setClients(clients);
    }
}
