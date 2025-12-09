package com.example.netty.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Camel Processor để xử lý business logic
 * Processor được gọi trong route để transform/process messages
 */
public class CamelHttpProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        // Lấy thông tin từ exchange
        String httpMethod = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
        String httpPath = exchange.getIn().getHeader("CamelHttpPath", String.class);
        String httpQuery = exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class);
        
        // Tạo response data
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Request processed by Apache Camel");
        response.put("timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
        
        // Request info
        Map<String, Object> requestInfo = new HashMap<>();
        requestInfo.put("method", httpMethod);
        requestInfo.put("path", httpPath);
        requestInfo.put("query", httpQuery);
        response.put("request", requestInfo);
        
        // Server info
        Map<String, Object> serverInfo = new HashMap<>();
        serverInfo.put("framework", "Apache Camel");
        serverInfo.put("component", "camel-netty-http");
        serverInfo.put("hostname", InetAddress.getLocalHost().getHostName());
        serverInfo.put("camelVersion", exchange.getContext().getVersion());
        response.put("server", serverInfo);
        
        // Processing info
        Map<String, Object> processingInfo = new HashMap<>();
        processingInfo.put("routeId", exchange.getFromRouteId());
        processingInfo.put("exchangeId", exchange.getExchangeId());
        processingInfo.put("processor", this.getClass().getSimpleName());
        response.put("processing", processingInfo);
        
        // Set content type and body
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setBody(response);
    }
}
