package com.example.springboottemplate.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final String FILE_PATH = "E:\\CODING\\wildfly-26.1.3.Final\\standalone\\input\\100MB.bin";

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() {
        File file = new File(FILE_PATH);

        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getName());
        headers.setContentLength(file.length());
        headers.set("Access-Control-Expose-Headers", "Content-Length, Content-Disposition");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}

