package com.example.springboottemplate.main;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.springboottemplate.WonderUtils;

public class JsonReader {
    public static void main(String[] args) {
        try {
            var str = WonderUtils.readTextFromFile("E:\\CODING\\java-is-fun\\extra\\springboot-template\\input\\test.json");
            System.out.println(str);
            
            // Read
            JSONObject jsonObject = new JSONObject(str);
            var a = jsonObject.getString("a");
            System.out.println("Value of a: " + a);

            JSONArray jsonArray = jsonObject.getJSONArray("b");
            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println("Value of b[" + i + "]: " + jsonArray.getString(i));
            }

            // Create
            jsonObject.put("c", "new value");
            jsonObject.put("d", "new value d");
            jsonArray.put("h");

            // Update
            jsonObject.put("a", "updated value");
            jsonArray.put(0, "g");

            // Delete
            jsonObject.remove("d");
            jsonArray.remove(1);

            System.out.println("----------------");
            System.out.println("After CUD JSON: " + jsonObject.toString());
        } catch (Exception e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
    }
}
