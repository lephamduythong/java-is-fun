package com.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasperReportGenerator {
    
    public static void main(String[] args) {
        try {
            // Đường dẫn đến file .jasper
            String jasperFile = "Blank_A4.jasper";
            
            // Đường dẫn file PDF output
            String outputFile = "output.pdf";
            
            // Tạo datasource với field DATA_ID
            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> dataRow = new HashMap<>();
            dataRow.put("CUSTOMER_NAME", "LÊ PHẠM DUY THÔNG#CÙI VĂN BẮP");
            dataList.add(dataRow);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
            
            // Parameters map (nếu cần)
            Map<String, Object> parameters = new HashMap<>();
            
            // Load file jasper (file đã compiled)
            System.out.println("Đang đọc file: " + jasperFile);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperFile));
            
            // Fill report với datasource
            System.out.println("Đang fill dữ liệu vào report...");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            
            // Export sang PDF
            System.out.println("Đang xuất file PDF: " + outputFile);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
            
            System.out.println("Hoàn thành! File PDF đã được tạo: " + outputFile);
            
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
