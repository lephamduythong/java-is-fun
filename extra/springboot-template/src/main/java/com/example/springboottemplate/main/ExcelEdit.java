package com.example.springboottemplate.main;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelEdit {

    private static final String FILE_PATH   = "E:/CODING/java-is-fun/extra/springboot-template/input/test.xlsx";
    private static final String OUTPUT_PATH = "E:/CODING/java-is-fun/extra/springboot-template/input/test_output.xlsx";

    /**
     * Load workbook từ file gốc, thực hiện các thao tác trên cùng 1 instance,
     * rồi xuất ra test_output.xlsx (file gốc không bị thay đổi).
     */
    public static void main(String[] args) throws IOException {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            readA1(workbook);
            createA2(workbook, "Giá trị mới A2");
            updateB2(workbook, "Đã cập nhật B2");
            deleteC2(workbook);
            mergeA4C4(workbook, "Ô được merge A4:C4");
            setFontA6(workbook, "Times New Roman");
            setBackgroundA8(workbook);
            setFormulaA10(workbook);
            replacePlaceholder(workbook, "{{my_name}}", "Nguyễn Văn A");

            saveWorkbook(workbook, OUTPUT_PATH);
            System.out.println("[SAVE]   Đã xuất bản copy: " + OUTPUT_PATH);
        }
    }

    /** Read: Đọc và in giá trị của ô A1 */
    public static void readA1(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);                          // hàng 1 (index 0)
        Cell cell = (row != null) ? row.getCell(0) : null; // cột A (index 0)
        System.out.println("[READ]   A1 = " + getCellValueAsString(cell));
    }

    /** Create: Ghi 1 giá trị mới vào ô A2 */
    public static void createA2(Workbook workbook, String newValue) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = getOrCreateRow(sheet, 1);                 // hàng 2 (index 1)
        Cell cell = row.createCell(0);                      // cột A (index 0)
        cell.setCellValue(newValue);
        System.out.println("[CREATE] A2 = " + newValue);
    }

    /** Update: Cập nhật giá trị tại ô B2 */
    public static void updateB2(Workbook workbook, String updatedValue) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = getOrCreateRow(sheet, 1);                 // hàng 2 (index 1)
        Cell cell = getOrCreateCell(row, 1);                // cột B (index 1)
        String oldValue = getCellValueAsString(cell);
        cell.setCellValue(updatedValue);
        System.out.println("[UPDATE] B2: \"" + oldValue + "\" → \"" + updatedValue + "\"");
    }

    /** Delete: Xóa giá trị tại ô C2 */
    public static void deleteC2(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);                          // hàng 2 (index 1)
        if (row != null) {
            Cell cell = row.getCell(2);                     // cột C (index 2)
            if (cell != null) {
                String oldValue = getCellValueAsString(cell);
                row.removeCell(cell);
                System.out.println("[DELETE] C2: \"" + oldValue + "\" đã bị xóa");
            } else {
                System.out.println("[DELETE] C2: ô trống, không cần xóa");
            }
        } else {
            System.out.println("[DELETE] C2: hàng 2 không tồn tại");
        }
    }

    /** Merge: Gộp cells A4:C4 và ghi giá trị vào ô đầu tiên */
    public static void mergeA4C4(Workbook workbook, String value) {
        Sheet sheet = workbook.getSheetAt(0);
        // CellRangeAddress(firstRow, lastRow, firstCol, lastCol) — index 0-based
        CellRangeAddress mergeRegion = new CellRangeAddress(3, 3, 0, 2); // A4:C4
        sheet.addMergedRegion(mergeRegion);

        Row row = getOrCreateRow(sheet, 3);   // hàng 4 (index 3)
        Cell cell = getOrCreateCell(row, 0);  // cột A (index 0) — ô đầu của vùng merge
        cell.setCellValue(value);
        System.out.println("[MERGE]  A4:C4 = \"" + value + "\"");
    }

    /** Font: Đổi font chữ của ô A6 thành font được chỉ định */
    public static void setFontA6(Workbook workbook, String fontName) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = getOrCreateRow(sheet, 5);   // hàng 6 (index 5)
        Cell cell = getOrCreateCell(row, 0);  // cột A (index 0)

        // Tạo font mới và gán tên
        Font font = workbook.createFont();
        font.setFontName(fontName);

        // Copy CellStyle hiện tại (tránh ảnh hưởng các ô khác) rồi gán font
        CellStyle style = workbook.createCellStyle();
        CellStyle existing = cell.getCellStyle();
        if (existing != null) {
            style.cloneStyleFrom(existing);
        }
        style.setFont(font);
        cell.setCellStyle(style);

        System.out.println("[FONT]   A6 font = " + fontName);
    }

    /** Background: Tô màu vàng cho ô A8 */
    public static void setBackgroundA8(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = getOrCreateRow(sheet, 7);   // hàng 8 (index 7)
        Cell cell = getOrCreateCell(row, 0);  // cột A (index 0)

        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        CellStyle existing = cell.getCellStyle();
        if (existing != null) {
            style.cloneStyleFrom(existing);
        }
        // RGB (255, 255, 0) = vàng
        style.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 0}, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        cell.setCellStyle(style);

        System.out.println("[BG]     A8 background = yellow (#FFFF00) + border THIN");
    }

    /** Formula: Đặt công thức SUM(B10:C10) vào ô A10 */
    public static void setFormulaA10(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = getOrCreateRow(sheet, 9);   // hàng 10 (index 9)
        Cell cell = getOrCreateCell(row, 0);  // cột A (index 0)
        cell.setCellFormula("SUM(B10:C10)");
        System.out.println("[FORMULA] A10 = SUM(B10:C10)");
    }

    /** Replace: Quét toàn bộ sheet, thay thế placeholder trong các ô text */
    public static void replacePlaceholder(Workbook workbook, String placeholder, String replacement) {
        int count = 0;
        for (int si = 0; si < workbook.getNumberOfSheets(); si++) {
            Sheet sheet = workbook.getSheetAt(si);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING) {
                        String original = cell.getStringCellValue();
                        if (original.contains(placeholder)) {
                            cell.setCellValue(original.replace(placeholder, replacement));
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println("[REPLACE] \"" + placeholder + "\" → \"" + replacement + "\" (" + count + " ô)");
    }

    // ---------- Helpers ----------

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "(trống)";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case NUMERIC: return new org.apache.poi.ss.usermodel.DataFormatter().formatCellValue(cell);
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default:      return "(trống)";
        }
    }

    private static Row getOrCreateRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        return (row != null) ? row : sheet.createRow(rowIndex);
    }

    private static Cell getOrCreateCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        return (cell != null) ? cell : row.createCell(colIndex);
    }

    private static void saveWorkbook(Workbook workbook, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }
    }
}
