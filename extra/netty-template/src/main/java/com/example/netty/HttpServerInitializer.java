package com.example.netty;

import com.example.netty.config.SslConfig;
import com.example.netty.middleware.LoggingMiddleware;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    
    private final boolean enableSsl;
    
    public HttpServerInitializer() {
        this(false);
    }
    
    public HttpServerInitializer(boolean enableSsl) {
        this.enableSsl = enableSsl;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        
        // SSL handler (if enabled)
        if (enableSsl) {
            SslConfig sslConfig = SslConfig.getInstance();
            if (sslConfig.isSslEnabled()) {
                SslContext sslContext = sslConfig.getSslContext();
                if (sslContext != null) {
                    pipeline.addLast(sslContext.newHandler(ch.alloc()));
                } else {
                    throw new IllegalStateException("SSL is enabled but SslContext is not initialized. Check server logs for SSL initialization errors.");
                }
            }
        }
        
        // HTTP codec
        pipeline.addLast(new HttpServerCodec());
        
        // HTTP aggregator - để có thể đọc full HTTP content
        pipeline.addLast(new HttpObjectAggregator(65536));
        
        // Logging middleware - log all requests and responses
        pipeline.addLast(new LoggingMiddleware());
        
        // Custom handler
        pipeline.addLast(new HttpServerHandler());
    }
}
