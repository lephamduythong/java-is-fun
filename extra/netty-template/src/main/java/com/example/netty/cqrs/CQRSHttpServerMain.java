package com.example.netty.cqrs;

import com.example.netty.cqrs.http.ProductCQRSHandler;
import com.example.netty.cqrs.interf.IProductRepository;
import com.example.netty.cqrs.mediator.CQRSMediator;
import com.example.netty.cqrs.repository.InMemoryProductRepository;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

/**
 * HTTP Server integrating CQRS Pattern
 * Starts a Netty server that handles CQRS operations via HTTP REST API
 */
public class CQRSHttpServerMain {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        // Initialize CQRS components
        IProductRepository repository = new InMemoryProductRepository();
        CQRSMediator mediator = new CQRSMediator(repository);
        ProductCQRSHandler productHandler = new ProductCQRSHandler(mediator);

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpServerCodec());
                        p.addLast(new HttpObjectAggregator(65536));
                        p.addLast(new CQRSServerHandler(productHandler));
                    }
                });

            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("=== CQRS HTTP Server Started ===");
            System.out.println("Server listening on http://localhost:" + PORT);
            System.out.println("\nAvailable endpoints:");
            System.out.println("  GET    http://localhost:" + PORT + "/api/products");
            System.out.println("  GET    http://localhost:" + PORT + "/api/products/{id}");
            System.out.println("  POST   http://localhost:" + PORT + "/api/products");
            System.out.println("  PUT    http://localhost:" + PORT + "/api/products/{id}");
            System.out.println("  DELETE http://localhost:" + PORT + "/api/products/{id}");
            System.out.println("\nPress Ctrl+C to stop the server\n");

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    static class CQRSServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        
        private final ProductCQRSHandler productHandler;

        public CQRSServerHandler(ProductCQRSHandler productHandler) {
            this.productHandler = productHandler;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
            String uri = request.uri();

            if (productHandler.canHandle(uri)) {
                productHandler.handle(ctx, request);
            } else {
                sendNotFound(ctx);
            }
        }

        private void sendNotFound(ChannelHandlerContext ctx) {
            String content = "{\"error\":\"Endpoint not found\"}";
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.NOT_FOUND,
                    io.netty.buffer.Unpooled.copiedBuffer(content, java.nio.charset.StandardCharsets.UTF_8)
            );
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.length());
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
