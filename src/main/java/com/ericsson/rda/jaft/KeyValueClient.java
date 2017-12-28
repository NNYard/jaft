package com.ericsson.rda.jaft;

import com.ericsson.rda.jaft.entity.GetKeyValueRequest;
import com.ericsson.rda.jaft.entity.SetKeyValueRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Eric Cen on 8/25/2017.
 */
public class KeyValueClient {

    private static final Logger logger = LoggerFactory.getLogger(KeyValueClient.class);

    private String host;
    private int port;
    private ChannelFuture channelFuture;
    private Channel channel;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private KeyValueHandler keyValueHandler = new KeyValueHandler();
    public KeyValueClient(String host, int port) throws InterruptedException {
        this.host = host;
        this.port = port;
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast("Encoder", new ObjectEncoder());
                pipeline.addLast("Decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                pipeline.addLast("Handler", keyValueHandler);
            }
        });
        logger.info(String.format("KeyValueClient: connect to [%s:%d]",host, port));
        channelFuture = bootstrap.connect(host, port).sync();
        channel = channelFuture.channel();


    }
    public String getValue(String nameSpace, String key) throws UnknownHostException, InterruptedException {
        GetKeyValueRequest request = new GetKeyValueRequest(nameSpace, key, InetAddress.getLocalHost().getHostName());
        ChannelPromise promise = keyValueHandler.sendMessage(request);
        promise.await();
        return keyValueHandler.getData();
    }

    public void setKeyValue(String nameSpace, String key, String value) throws InterruptedException {
        SetKeyValueRequest request = new SetKeyValueRequest(key, value,nameSpace);
        ChannelPromise promise = keyValueHandler.sendMessage(request);
        channel.close();
        eventLoopGroup.shutdownGracefully();
    }
}
