package com.example.netty;

import com.example.netty.config.SslConfig;
import com.example.netty.session.SessionManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerApplication {
    
    private static final int HTTP_PORT = 8080;
    private static final int HTTPS_PORT = 8443;
    
    public static void main(String[] args) throws Exception {
        SslConfig sslConfig = SslConfig.getInstance();
        boolean sslEnabled = sslConfig.isSslEnabled();
        
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
            // Start HTTP server
            ServerBootstrap httpBootstrap = new ServerBootstrap();
            httpBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer(false))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            
            System.out.println("Netty HTTP Server starting on port " + HTTP_PORT + "...");
            ChannelFuture httpFuture = httpBootstrap.bind(HTTP_PORT).sync();
            System.out.println("HTTP Server started successfully on port " + HTTP_PORT);
            
            // Start HTTPS server if SSL is enabled
            ChannelFuture httpsFuture = null;
            if (sslEnabled) {
                ServerBootstrap httpsBootstrap = new ServerBootstrap();
                httpsBootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new HttpServerInitializer(true))
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);
                
                System.out.println("Netty HTTPS Server starting on port " + HTTPS_PORT + "...");
                httpsFuture = httpsBootstrap.bind(HTTPS_PORT).sync();
                System.out.println("HTTPS Server started successfully on port " + HTTPS_PORT);
            }
            
            // Check if SQLite is enabled
            SessionManager sessionManager = SessionManager.getInstance();
            if (sessionManager.isUsingSqlite()) {
                System.out.println("SQLite persistent session storage: ENABLED");
            } else {
                System.out.println("In-memory session storage: ENABLED");
                System.out.println("To enable SQLite storage, set environment variable: USE_SQLITE_SESSION=true");
            }
            
            System.out.println("\n=== Server Information ===");
            System.out.println("Try these endpoints:");
            System.out.println("  GET  http://localhost:" + HTTP_PORT + "/login");
            System.out.println("  GET  http://localhost:" + HTTP_PORT + "/hello");
            System.out.println("  POST http://localhost:" + HTTP_PORT + "/data");
            
            if (sslEnabled) {
                System.out.println("\nHTTPS endpoints:");
                System.out.println("  GET  https://localhost:" + HTTPS_PORT + "/login");
                System.out.println("  GET  https://localhost:" + HTTPS_PORT + "/hello");
                System.out.println("  POST https://localhost:" + HTTPS_PORT + "/data");
                System.out.println("\nNote: Self-signed certificate warnings are expected in browsers.");
            }
            System.out.println("==========================\n");
            
            // Wait for both servers to close
            httpFuture.channel().closeFuture().sync();
            if (httpsFuture != null) {
                httpsFuture.channel().closeFuture().sync();
            }
        } finally {
            SessionManager.getInstance().shutdown();
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
