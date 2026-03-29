package com.example.springboottemplate.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@RestController
public class DocumentController {

    @GetMapping("/download-document")
    public ResponseEntity<byte[]> getDocument() throws Exception {
        ClassPathResource resource = new ClassPathResource("static/template/don-xin-phep-nghi-hoc.docx");

        try (InputStream inputStream = resource.getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream)) {

            // Replace in body paragraphs
            replaceInParagraphs(document.getParagraphs());

            // Replace in table cells
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        replaceInParagraphs(cell.getParagraphs());
                    }
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.write(out);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            headers.setContentDispositionFormData("attachment", "don-xin-phep-nghi-hoc.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(out.toByteArray());
        }
    }

    private void replaceInParagraphs(List<XWPFParagraph> paragraphs) {
        for (XWPFParagraph paragraph : paragraphs) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains("{{1}}")) {
                    run.setText(text.replace("{{1}}", "Nguy\u1ec5n V\u0103n A"), 0);
                }
            }
        }
    }
}
