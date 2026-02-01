package com.example.springboottemplate.main;

import com.example.springboottemplate.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MergeFiles {
    
    public static void main(String[] args) {
        String uploadFolder = "E:\\CODING\\Upload";
        String mergedFolder = "E:\\CODING\\Upload\\merged";
        
        try {
            System.out.println("Starting file merge process...");
            System.out.println("Upload folder: " + uploadFolder);
            System.out.println("Output folder: " + mergedFolder);
            System.out.println();
            
            // Create merged folder if it doesn't exist
            File mergedDir = new File(mergedFolder);
            if (!mergedDir.exists()) {
                if (!mergedDir.mkdirs()) {
                    System.err.println("Error: Failed to create merged directory");
                    return;
                }
                System.out.println("Created merged directory: " + mergedFolder);
            }
            
            // Scan upload folder
            File uploadDir = new File(uploadFolder);
            if (!uploadDir.exists() || !uploadDir.isDirectory()) {
                System.err.println("Error: Upload folder does not exist or is not a directory");
                return;
            }
            
            File[] files = uploadDir.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("No files found in upload folder");
                return;
            }
            
            System.out.println("Found " + files.length + " files in upload folder");
            System.out.println();
            
            // Group files by base name (before .partXXX)
            Map<String, List<File>> fileGroups = new HashMap<>();
            Pattern partPattern = Pattern.compile("^(.+)\\.part(\\d+)$");
            
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    Matcher matcher = partPattern.matcher(fileName);
                    
                    if (matcher.matches()) {
                        String baseName = matcher.group(1);
                        
                        fileGroups.putIfAbsent(baseName, new ArrayList<>());
                        fileGroups.get(baseName).add(file);
                    } else {
                        System.out.println("Skipping non-part file: " + fileName);
                    }
                }
            }
            
            if (fileGroups.isEmpty()) {
                System.out.println("No part files found to merge");
                return;
            }
            
            System.out.println("Found " + fileGroups.size() + " file(s) to merge:");
            for (String baseName : fileGroups.keySet()) {
                System.out.println("  - " + baseName + " (" + fileGroups.get(baseName).size() + " parts)");
            }
            System.out.println();
            
            // Merge each group
            int successCount = 0;
            int failCount = 0;
            
            for (Map.Entry<String, List<File>> entry : fileGroups.entrySet()) {
                String baseName = entry.getKey();
                List<File> parts = entry.getValue();
                
                System.out.println("Merging: " + baseName);
                System.out.println("  Parts: " + parts.size());
                
                try {
                    // Sort parts by part number
                    Collections.sort(parts, (f1, f2) -> {
                        String name1 = f1.getName();
                        String name2 = f2.getName();
                        
                        Matcher m1 = partPattern.matcher(name1);
                        Matcher m2 = partPattern.matcher(name2);
                        
                        if (m1.matches() && m2.matches()) {
                            int partNum1 = Integer.parseInt(m1.group(2));
                            int partNum2 = Integer.parseInt(m2.group(2));
                            return Integer.compare(partNum1, partNum2);
                        }
                        
                        return name1.compareTo(name2);
                    });
                    
                    // Build list of part file paths
                    List<String> partPaths = new ArrayList<>();
                    for (File part : parts) {
                        partPaths.add(part.getAbsolutePath());
                    }
                    
                    // Merge files
                    String outputPath = mergedFolder + File.separator + baseName;
                    Utils.mergeFiles(partPaths, outputPath);
                    
                    // Get merged file size
                    double mergedSizeMB = Utils.getFileSizeInMB(outputPath);
                    
                    System.out.println("  Output: " + outputPath);
                    System.out.println("  Size: " + mergedSizeMB + " MB");
                    System.out.println("  Status: SUCCESS");
                    successCount++;
                    
                } catch (Exception e) {
                    System.err.println("  Status: FAILED");
                    System.err.println("  Error: " + e.getMessage());
                    failCount++;
                }
                
                System.out.println();
            }
            
            // Summary
            System.out.println("=".repeat(60));
            System.out.println("Merge Summary:");
            System.out.println("Total files: " + fileGroups.size());
            System.out.println("Success: " + successCount);
            System.out.println("Failed: " + failCount);
            System.out.println();
            System.out.println("Merge process completed!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
