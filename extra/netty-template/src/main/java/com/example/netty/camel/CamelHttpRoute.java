package com.example.netty.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * Camel Route định nghĩa các HTTP endpoints và routing logic
 * Sử dụng Netty HTTP component để xử lý HTTP requests
 */
public class CamelHttpRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        // Configure REST DSL
        restConfiguration()
            .component("netty-http")
            .host("localhost")
            .port(9090)
            .bindingMode(RestBindingMode.json);
        
        // Global exception handling
        onException(Exception.class)
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setBody()
                .constant("{\"status\":\"error\",\"message\":\"Internal server error\"}");
        
        // Route 1: GET /camel/hello - Simple greeting endpoint
        from("netty-http:http://0.0.0.0:9090/camel/hello?matchOnUriPrefix=true")
            .routeId("camel-hello-route")
            .log("Received request: ${header.CamelHttpMethod} ${header.CamelHttpPath}")
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setBody()
                .constant("{\"message\":\"Hello from Apache Camel!\",\"framework\":\"Camel + Netty HTTP\",\"timestamp\":${date:now:yyyy-MM-dd'T'HH:mm:ss}}")
            .log("Response sent: ${body}");
        
        // Route 2: GET /camel/info - Return server information
        from("netty-http:http://0.0.0.0:9090/camel/info?matchOnUriPrefix=true")
            .routeId("camel-info-route")
            .log("Info endpoint called")
            .process(new CamelHttpProcessor())
            .marshal().json();
        
        // Route 3: POST /camel/echo - Echo back the request body
        from("netty-http:http://0.0.0.0:9090/camel/echo?matchOnUriPrefix=true")
            .routeId("camel-echo-route")
            .log("Echo endpoint - Received: ${body}")
            .choice()
                .when(header(Exchange.HTTP_METHOD).isEqualTo("POST"))
                    .unmarshal().json()
                    .process(exchange -> {
                        Object body = exchange.getIn().getBody();
                        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
                        exchange.getIn().setBody(String.format(
                            "{\"status\":\"success\",\"echo\":%s,\"receivedAt\":\"%s\"}",
                            body.toString(),
                            new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new java.util.Date())
                        ));
                    })
                    .endChoice()
                .otherwise()
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(405))
                    .setBody()
                        .constant("{\"status\":\"error\",\"message\":\"Method not allowed. Use POST\"}");
        
        // Route 4: GET /camel/transform/{name} - Transform request with path parameter
        from("netty-http:http://0.0.0.0:9090/camel/transform/*?matchOnUriPrefix=true")
            .routeId("camel-transform-route")
            .log("Transform endpoint - Path: ${header.CamelHttpPath}")
            .process(exchange -> {
                String path = exchange.getIn().getHeader("CamelHttpPath", String.class);
                String name = path.replace("/camel/transform/", "");
                
                if (name.isEmpty() || name.equals("/camel/transform/")) {
                    name = "Guest";
                }
                
                String response = String.format(
                    "{\"greeting\":\"Welcome, %s!\",\"transformed\":true,\"upperCase\":\"%s\",\"length\":%d}",
                    name, name.toUpperCase(), name.length()
                );
                
                exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
                exchange.getIn().setBody(response);
            });
        
        // Route 5: GET /camel/delay/{seconds} - Simulate slow endpoint
        from("netty-http:http://0.0.0.0:9090/camel/delay/*?matchOnUriPrefix=true")
            .routeId("camel-delay-route")
            .log("Delay endpoint called")
            .process(exchange -> {
                String path = exchange.getIn().getHeader("CamelHttpPath", String.class);
                String delayStr = path.replace("/camel/delay/", "");
                
                int delay = 1;
                try {
                    delay = Integer.parseInt(delayStr);
                    if (delay < 1) delay = 1;
                    if (delay > 10) delay = 10; // Max 10 seconds
                } catch (NumberFormatException e) {
                    delay = 1;
                }
                
                exchange.setProperty("delaySeconds", delay);
            })
            .log("Delaying for ${exchangeProperty.delaySeconds} seconds...")
            .delay(simple("${exchangeProperty.delaySeconds}000"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setBody()
                .simple("{\"message\":\"Delayed response\",\"delaySeconds\":${exchangeProperty.delaySeconds},\"completedAt\":\"${date:now:yyyy-MM-dd'T'HH:mm:ss}\"}");
        
        // Route 6: POST /camel/validate - Validate JSON payload
        from("netty-http:http://0.0.0.0:9090/camel/validate?matchOnUriPrefix=true")
            .routeId("camel-validate-route")
            .log("Validate endpoint called")
            .choice()
                .when(header(Exchange.HTTP_METHOD).isNotEqualTo("POST"))
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(405))
                    .setBody().constant("{\"status\":\"error\",\"message\":\"Method not allowed. Use POST\"}")
                    .endChoice()
                .otherwise()
                    .doTry()
                        .unmarshal().json()
                        .process(exchange -> {
                            @SuppressWarnings("unchecked")
                            java.util.Map<String, Object> data = exchange.getIn().getBody(java.util.Map.class);
                            
                            boolean valid = data.containsKey("email") && data.containsKey("name");
                            String message = valid ? "Validation passed" : "Missing required fields: email, name";
                            
                            String response = String.format(
                                "{\"status\":\"%s\",\"message\":\"%s\",\"data\":%s}",
                                valid ? "success" : "error",
                                message,
                                exchange.getContext().getTypeConverter().convertTo(String.class, data)
                            );
                            
                            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
                            exchange.getIn().setBody(response);
                        })
                    .doCatch(Exception.class)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                        .setBody().constant("{\"status\":\"error\",\"message\":\"Invalid JSON payload\"}");
        
        // Route 7: GET /camel/stats - Return route statistics
        from("netty-http:http://0.0.0.0:9090/camel/stats?matchOnUriPrefix=true")
            .routeId("camel-stats-route")
            .log("Stats endpoint called")
            .process(exchange -> {
                long uptime = exchange.getContext().getUptimeMillis();
                int routeCount = exchange.getContext().getRoutes().size();
                
                String response = String.format(
                    "{\"camelVersion\":\"%s\",\"uptimeSeconds\":%d,\"routeCount\":%d,\"status\":\"running\"}",
                    exchange.getContext().getVersion(),
                    uptime / 1000,
                    routeCount
                );
                
                exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
                exchange.getIn().setBody(response);
            });
    }
}
