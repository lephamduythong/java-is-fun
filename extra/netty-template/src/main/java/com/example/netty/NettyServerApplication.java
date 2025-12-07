package com.example.netty;

import com.example.netty.session.SessionManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerApplication {
    
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        // Add shutdown hook for graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down gracefully...");
            SessionManager.getInstance().shutdown();
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }));
        
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            
            System.out.println("Netty HTTP Server starting on port " + PORT + "...");
            
            // Check if SQLite is enabled
            SessionManager sessionManager = SessionManager.getInstance();
            if (sessionManager.isUsingSqlite()) {
                System.out.println("SQLite persistent session storage: ENABLED");
            } else {
                System.out.println("In-memory session storage: ENABLED");
                System.out.println("To enable SQLite storage, set environment variable: USE_SQLITE_SESSION=true");
            }
            
            ChannelFuture future = bootstrap.bind(PORT).sync();
            
            System.out.println("Server started successfully!");
            System.out.println("Try:");
            System.out.println("  GET  http://localhost:" + PORT + "/login");
            System.out.println("  GET  http://localhost:" + PORT + "/hello");
            System.out.println("  POST http://localhost:" + PORT + "/data");
            
            future.channel().closeFuture().sync();
        } finally {
            SessionManager.getInstance().shutdown();
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
