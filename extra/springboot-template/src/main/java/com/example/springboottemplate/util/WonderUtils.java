package com.example.springboottemplate.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;


public class WonderUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, String> createErrorJson(String error, String message) {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("error", error);
        errorData.put("message", message);
        return errorData;
    }
    
    public static Map<String, String> parseQueryParams(String uri) {
        Map<String, String> params = new HashMap<>();
        
        int queryStart = uri.indexOf('?');
        if (queryStart == -1) {
            return params;
        }
        
        String query = uri.substring(queryStart + 1);
        String[] pairs = query.split("&");
        
        for (String pair : pairs) {
            int idx = pair.indexOf('=');
            if (idx > 0) {
                try {
                    String key = java.net.URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                    String value = java.net.URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                    params.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return params;
    }
}
