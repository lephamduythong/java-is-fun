package com.thong;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PostgreSQLConnection {

    public static void main(String[] args) throws SQLException, IOException {
        // try {
        //     ArrayList<String[]> results = queryMyTable();
        //     for (String[] row : results) {
        //         System.out.println(String.join(", ", row));
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        // generateData();
        exportMyTableToTxt("E:\\DATA\\my_table_data.txt");
    }
    
    /**
     * Create a connection to PostgreSQL using HikariCP
     */
    public static Connection getConnection() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setDriverClassName("org.postgresql.Driver");

        HikariDataSource ds = new HikariDataSource(config);
        return ds.getConnection();
    }

    /**
     * Query MY_TABLE and return results as a list of String arrays (each array is a row)
     */
    public static ArrayList<String[]> queryMyTable() throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM MY_TABLE")) {

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                results.add(row);
            }
        }
        return results;
    }

    /**
     * Generate 20,000 rows into MY_TABLE (id: uuid, val: any value)
     */
    public static void generateData() throws SQLException {
        String sql = "INSERT INTO MY_TABLE(id, val) VALUES (?, ?)";
        try (Connection conn = getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 500000; i++) {
                // Sử dụng setObject với UUID cho cột id kiểu uuid
                ps.setObject(1, UUID.randomUUID());
                ps.setString(2, "Value " + i);
                ps.addBatch();
                if (i % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        }
    }

    /**
     * Export all data from MY_TABLE to a .txt file with '|' delimiter
     */
    public static void exportMyTableToTxt(String filePath) throws SQLException, IOException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM MY_TABLE");
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            int columnCount = rs.getMetaData().getColumnCount();

            // Write header
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) header.append("|");
                header.append(rs.getMetaData().getColumnName(i));
            }
            writer.write(header.toString());
            writer.newLine();

            // Write data rows
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) sb.append("|");
                    sb.append(rs.getString(i));
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }
}
