package com.example.springboottemplate.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpsertBatchDB {

    /**
     * Upsert (INSERT hoặc UPDATE) một danh sách bản ghi vào bảng SQLite.
     *
     * Logic:
     * - Nếu giá trị của cột idColumn trong row là null/rỗng → INSERT (tạo mới)
     * - Nếu có giá trị idColumn → UPDATE theo điều kiện WHERE idColumn = ?
     *
     * @param conn      Active JDBC Connection (SQLite hoặc DB khác)
     * @param tableName Tên bảng cần thao tác
     * @param dataList  Danh sách row dữ liệu (key = tên cột, value = giá trị)
     * @param idColumn  Tên cột định danh (primary key)
     * @param mode      Chế độ xử lý: "INSERT" | "UPDATE" | "UPSERT"
     * @throws SQLException nếu có lỗi SQL
     */
    public static void updateTableDB(
            Connection conn,
            String tableName,
            List<HashMap<String, String>> dataList,
            String idColumn,
            String mode
        ) throws SQLException {
        
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        if (!"INSERT".equals(mode) && !"UPDATE".equals(mode) && !"UPSERT".equals(mode)) {
            throw new IllegalArgumentException("mode phải là INSERT, UPDATE hoặc UPSERT, nhận được: " + mode);
        }

        boolean hasIdColumn = dataList.stream().anyMatch(r -> r.containsKey(idColumn));
        if (!hasIdColumn) {
            System.out.println("[SKIP] Không có row nào chứa cột idColumn: " + idColumn);
            return;
        }

        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            for (HashMap<String, String> row : dataList) {
                String idValue = row.get(idColumn);
                boolean isNew = (idValue == null || idValue.trim().isEmpty());

                // Lọc theo mode
                if ("INSERT".equals(mode) && !isNew) {
                    System.out.println("[SKIP-MODE INSERT] Row đã có id, bỏ qua: " + row);
                    continue;
                }
                if ("UPDATE".equals(mode) && isNew) {
                    System.out.println("[SKIP-MODE UPDATE] Row chưa có id, bỏ qua: " + row);
                    continue;
                }

                if (isNew) {
                    // ── INSERT ──────────────────────────────────────────────
                    List<String> columns = new ArrayList<>(row.keySet());

                    StringBuilder sql = new StringBuilder("INSERT INTO ")
                            .append(tableName).append(" (");
                    StringBuilder placeholders = new StringBuilder();

                    for (int i = 0; i < columns.size(); i++) {
                        sql.append(columns.get(i));
                        placeholders.append("?");
                        if (i < columns.size() - 1) {
                            sql.append(", ");
                            placeholders.append(", ");
                        }
                    }
                    sql.append(") VALUES (").append(placeholders).append(")");

                    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                        for (int i = 0; i < columns.size(); i++) {
                            ps.setString(i + 1, row.get(columns.get(i)));
                        }
                        ps.executeUpdate();
                        System.out.println("[INSERT] " + row);
                    }

                } else {
                    // ── UPDATE ──────────────────────────────────────────────
                    List<String> updateCols = new ArrayList<>();
                    for (String col : row.keySet()) {
                        if (!col.equals(idColumn)) {
                            updateCols.add(col);
                        }
                    }
                    if (updateCols.isEmpty()) {
                        System.out.println("[SKIP] Row chỉ có idColumn, không có gì để update: " + row);
                        continue;
                    }

                    StringBuilder sql = new StringBuilder("UPDATE ")
                            .append(tableName).append(" SET ");
                    for (int i = 0; i < updateCols.size(); i++) {
                        sql.append(updateCols.get(i)).append(" = ?");
                        if (i < updateCols.size() - 1)
                            sql.append(", ");
                    }
                    sql.append(" WHERE ").append(idColumn).append(" = ?");

                    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                        for (int i = 0; i < updateCols.size(); i++) {
                            ps.setString(i + 1, row.get(updateCols.get(i)));
                        }
                        ps.setString(updateCols.size() + 1, idValue);
                        int affected = ps.executeUpdate();
                        if (affected == 0) {
                            // Bản ghi chưa tồn tại → INSERT
                            List<String> allCols = new ArrayList<>(row.keySet());
                            StringBuilder insertSql = new StringBuilder("INSERT INTO ")
                                    .append(tableName).append(" (");
                            StringBuilder placeholders = new StringBuilder();
                            for (int i = 0; i < allCols.size(); i++) {
                                insertSql.append(allCols.get(i));
                                placeholders.append("?");
                                if (i < allCols.size() - 1) {
                                    insertSql.append(", ");
                                    placeholders.append(", ");
                                }
                            }
                            insertSql.append(") VALUES (").append(placeholders).append(")");
                            try (PreparedStatement ins = conn.prepareStatement(insertSql.toString())) {
                                for (int i = 0; i < allCols.size(); i++) {
                                    ins.setString(i + 1, row.get(allCols.get(i)));
                                }
                                ins.executeUpdate();
                                System.out.println("[INSERT fallback] " + row);
                            }
                        } else {
                            System.out.println("[UPDATE] affected=" + affected + " | " + row);
                        }
                    }
                }
            }
            conn.commit();
            System.out.println("[TRANSACTION] commit thành công.");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("[TRANSACTION] rollback do lỗi: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(prevAutoCommit);
        }
    }

    // ── DEMO với SQLite ─────────────────────────────────────────────────────

    public static void main(String[] args) throws SQLException {
        // Kết nối SQLite file-based (tạo mới nếu chưa có)
        String url = "jdbc:sqlite:test_upsert.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            // Tạo bảng demo (DDL chạy ngoài transaction)
            try (Statement st = conn.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS employees ("
                        + "emp_id   TEXT PRIMARY KEY, "
                        + "name     TEXT, "
                        + "dept     TEXT, "
                        + "salary   TEXT)");
            }

            // Dữ liệu cần upsert
            List<HashMap<String, String>> data = new ArrayList<>();

            // Row 1: INSERT
            HashMap<String, String> row1 = new HashMap<>();
            row1.put("emp_id", "E001");
            row1.put("name", "Nguyen Van A");
            row1.put("dept", "IT");
            row1.put("salary", "15000000");
            data.add(row1);

            // Row 2: INSERT
            HashMap<String, String> row2 = new HashMap<>();
            row2.put("emp_id", "E002");
            row2.put("name", "Tran Thi B");
            row2.put("dept", "HR");
            row2.put("salary", "12000000");
            data.add(row2);

            // Row 3: có emp_id = "E001" → UPDATE (phải tồn tại trước)
            HashMap<String, String> row3 = new HashMap<>();
            row3.put("emp_id", "E001");
            row3.put("dept", "Finance");
            row3.put("salary", "20000000");
            data.add(row3);

            updateTableDB(conn, "employees", data, "emp_id", "UPSERT");
        }
    }
}
