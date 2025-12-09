package com.example.netty.middleware;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Middleware để log tất cả HTTP requests và responses
 */
public class LoggingMiddleware extends ChannelDuplexHandler {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private long requestTime;
    private String requestInfo;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            requestTime = System.currentTimeMillis();
            FullHttpRequest request = (FullHttpRequest) msg;
            
            // Log request
            StringBuilder sb = new StringBuilder();
            sb.append("\n========== INCOMING REQUEST ==========\n");
            sb.append("Time: ").append(DATE_FORMAT.format(new Date(requestTime))).append("\n");
            sb.append("Method: ").append(request.method()).append("\n");
            sb.append("URI: ").append(request.uri()).append("\n");
            sb.append("HTTP Version: ").append(request.protocolVersion()).append("\n");
            sb.append("Headers:\n");
            
            for (String name : request.headers().names()) {
                sb.append("  ").append(name).append(": ").append(request.headers().get(name)).append("\n");
            }
            
            // Log request body if present
            ByteBuf content = request.content();
            if (content.isReadable()) {
                String body = content.toString(StandardCharsets.UTF_8);
                sb.append("Body:\n");
                sb.append("  ").append(body).append("\n");
            }
            
            sb.append("======================================\n");
            requestInfo = request.method() + " " + request.uri();
            System.out.println(sb.toString());
        }
        
        // Pass to next handler
        super.channelRead(ctx, msg);
    }
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            long responseTime = System.currentTimeMillis();
            long duration = responseTime - requestTime;
            
            // Log response
            StringBuilder sb = new StringBuilder();
            sb.append("\n========== OUTGOING RESPONSE ==========\n");
            sb.append("Time: ").append(DATE_FORMAT.format(new Date(responseTime))).append("\n");
            sb.append("Request: ").append(requestInfo != null ? requestInfo : "Unknown").append("\n");
            sb.append("Duration: ").append(duration).append("ms\n");
            sb.append("Status: ").append(response.status()).append("\n");
            sb.append("Headers:\n");
            
            for (String name : response.headers().names()) {
                sb.append("  ").append(name).append(": ").append(response.headers().get(name)).append("\n");
            }
            
            // Log response body if present
            ByteBuf content = response.content();
            if (content.isReadable()) {
                // Create a copy to read without affecting the original
                ByteBuf copy = content.duplicate();
                String body = copy.toString(StandardCharsets.UTF_8);
                
                // Truncate long responses
                if (body.length() > 1000) {
                    body = body.substring(0, 1000) + "... (truncated)";
                }
                
                sb.append("Body:\n");
                sb.append("  ").append(body).append("\n");
            }
            
            sb.append("=======================================\n");
            System.out.println(sb.toString());
        }
        
        // Pass to next handler
        super.write(ctx, msg, promise);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("\n========== EXCEPTION IN MIDDLEWARE ==========");
        System.err.println("Time: " + DATE_FORMAT.format(new Date()));
        System.err.println("Request: " + (requestInfo != null ? requestInfo : "Unknown"));
        System.err.println("Error: " + cause.getMessage());
        cause.printStackTrace();
        System.err.println("=============================================\n");
        
        super.exceptionCaught(ctx, cause);
    }
}
