package com.example.netty;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    
    private final Gson gson = new Gson();
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpMethod method = request.method();
        
        System.out.println("Received " + method + " request for: " + uri);
        
        FullHttpResponse response;
        
        // Handle GET request
        if (method == HttpMethod.GET && uri.startsWith("/hello")) {
            response = handleGetRequest(request);
        }
        // Handle POST request
        else if (method == HttpMethod.POST && uri.startsWith("/data")) {
            response = handlePostRequest(request);
        }
        // 404 Not Found
        else {
            response = createJsonResponse(NOT_FOUND, 
                    createErrorJson("Not Found", "The requested endpoint does not exist"));
        }
        
        // Send response and close connection
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    private FullHttpResponse handleGetRequest(FullHttpRequest request) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "Hello from Netty Server!");
        responseData.put("method", "GET");
        responseData.put("timestamp", System.currentTimeMillis());
        responseData.put("status", "success");
        
        return createJsonResponse(OK, responseData);
    }
    
    private FullHttpResponse handlePostRequest(FullHttpRequest request) {
        String contentBody = request.content().toString(CharsetUtil.UTF_8);
        
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "Data received successfully");
        responseData.put("method", "POST");
        responseData.put("receivedData", contentBody);
        responseData.put("timestamp", System.currentTimeMillis());
        responseData.put("status", "success");
        
        // Parse received JSON if possible
        try {
            if (contentBody != null && !contentBody.isEmpty()) {
                JsonObject receivedJson = gson.fromJson(contentBody, JsonObject.class);
                responseData.put("parsedData", receivedJson);
            }
        } catch (Exception e) {
            responseData.put("parseError", "Could not parse JSON: " + e.getMessage());
        }
        
        return createJsonResponse(OK, responseData);
    }
    
    private Map<String, String> createErrorJson(String error, String message) {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("error", error);
        errorData.put("message", message);
        return errorData;
    }
    
    private FullHttpResponse createJsonResponse(HttpResponseStatus status, Object data) {
        String jsonResponse = gson.toJson(data);
        
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1,
                status,
                Unpooled.copiedBuffer(jsonResponse, CharsetUtil.UTF_8)
        );
        
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        
        return response;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
