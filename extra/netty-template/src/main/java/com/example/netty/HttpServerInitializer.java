package com.example.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        
        // HTTP codec
        pipeline.addLast(new HttpServerCodec());
        
        // HTTP aggregator - để có thể đọc full HTTP content
        pipeline.addLast(new HttpObjectAggregator(65536));
        
        // Custom handler
        pipeline.addLast(new HttpServerHandler());
    }
}
