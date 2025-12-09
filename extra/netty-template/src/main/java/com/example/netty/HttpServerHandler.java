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
        
        FullHttpResponse response;
        
        // Route handling by HTTP method
        if (method == HttpMethod.GET) {
            response = handleGetRequest(request, uri);
        } else if (method == HttpMethod.POST) {
            response = handlePostRequest(request, uri);
        } else if (method == HttpMethod.PUT) {
            response = handlePutRequest(request, uri);
        } else if (method == HttpMethod.DELETE) {
            response = handleDeleteRequest(request, uri);
        } else {
            response = WonderUtils.createJsonResponse(NOT_FOUND, WonderUtils.createErrorJson("Not Found", "The requested endpoint does not exist"));
        }
        
        // Send response and close connection
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    private FullHttpResponse handleGetRequest(FullHttpRequest request, String uri) {
        switch (uri) {
            case "/login": {
                var oauthHandler = new OAuthHandler();
                return oauthHandler.handleLoginRequest();
            }
            case "/profile": {
                var oauthHandler = new OAuthHandler();
                return oauthHandler.handleProfileRequest(request);
            }
            case "/logout": {
                var oauthHandler = new OAuthHandler();
                return oauthHandler.handleLogoutRequest(request);
            }
            default: {
                if (uri.startsWith("/oauth/callback")) {
                    var oauthHandler = new OAuthHandler();
                    return oauthHandler.handleOAuthCallback(uri);
                } else if (uri.startsWith("/hello")) {
                    var appHandler = new AppHandler();
                    return appHandler.handleGetRequest(request);
                } else {
                    return WonderUtils.createJsonResponse(NOT_FOUND,
                            WonderUtils.createErrorJson("Not Found", "The requested endpoint does not exist"));
                }
            }
        }
    }
    
    private FullHttpResponse handlePostRequest(FullHttpRequest request, String uri) {
        if (uri.startsWith("/data")) {
            var appHandler = new AppHandler();
            return appHandler.handlePostRequest(request);
        } else {
            return WonderUtils.createJsonResponse(NOT_FOUND,
                    WonderUtils.createErrorJson("Not Found", "The requested endpoint does not exist"));
        }
    }
    
    private FullHttpResponse handlePutRequest(FullHttpRequest request, String uri) {
        return WonderUtils.createJsonResponse(NOT_FOUND,
                WonderUtils.createErrorJson("Not Found", "The requested endpoint does not exist"));
    }
    
    private FullHttpResponse handleDeleteRequest(FullHttpRequest request, String uri) {
        return WonderUtils.createJsonResponse(NOT_FOUND,
                WonderUtils.createErrorJson("Not Found", "The requested endpoint does not exist"));
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
