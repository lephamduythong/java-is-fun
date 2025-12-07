package com.example.netty;

import com.example.netty.common.WonderUtils;
import com.example.netty.handler.AppHandler;
import com.example.netty.handler.OAuthHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpMethod method = request.method();
        
        System.out.println("Received " + method + " request for: " + uri);
        
        FullHttpResponse response;
        
        // OAuth routes
        if (method == HttpMethod.GET && uri.equals("/login")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleLoginRequest();
        }
        else if (method == HttpMethod.GET && uri.startsWith("/oauth/callback")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleOAuthCallback(uri);
        }
        else if (method == HttpMethod.GET && uri.equals("/profile")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleProfileRequest(request);
        }
        else if (method == HttpMethod.GET && uri.equals("/logout")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleLogoutRequest(request);
        }
        // Protected routes - require authentication
        else if (method == HttpMethod.GET && uri.startsWith("/hello")) {
            var appHandler = new AppHandler();
            response = appHandler.handleGetRequest(request);
        }
        else if (method == HttpMethod.POST && uri.startsWith("/data")) {
            var appHandler = new AppHandler();
            response = appHandler.handlePostRequest(request);
        }
        // 404 Not Found
        else {
            response = WonderUtils.createJsonResponse(NOT_FOUND, 
                    WonderUtils.createErrorJson("Not Found", "The requested endpoint does not exist"));
        }
        
        // Send response and close connection
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
