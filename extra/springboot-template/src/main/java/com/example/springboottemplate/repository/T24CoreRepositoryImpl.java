package com.example.springboottemplate.repository;
import com.example.springboottemplate.repository.intf.T24CoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;

public class T24CoreRepositoryImpl implements T24CoreRepository {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    private String T24_DB_JNDI_NAME = "java:jboss/datasources/PostgresDS";
    private String SQL_SELECT_XML_BY_RECID = "SELECT xmlrecord FROM test.%s WHERE recid = ?";
    private String SQL_SELECT_XML_BY_RECID_WITH_FIELDS = "SELECT %s FROM test.%s WHERE recid = ?";

    private Connection getConnection() throws SQLException, NamingException {
        return ((DataSource) new InitialContext().lookup(T24_DB_JNDI_NAME)).getConnection();
    }

    @Override
    public T24CoreRecordEntity getRecordByRecid(String tableName, String recid) {
        if (!tableName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid table name");
        }
        try (
            final var conn = getConnection(); 
            final var stmt = conn.prepareStatement(String.format(SQL_SELECT_XML_BY_RECID, tableName))
        ) {
            stmt.setString(1, recid);
            try (final var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    var jsonObj = objectMapper.readTree(rs.getString(1));
                    return new T24CoreRecordEntity(recid, jsonObj);
                }
            }
            return new T24CoreRecordEntity(); // Replace with actual query and mapping logic
        } catch (Exception e) {
            System.err.println("Error occurred while fetching record by recid: " + recid + " from table: " + tableName + ". Error: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T24CoreRecordEntity getRecordByRecid(String tableName, List<String> fieldNameList, String recid) {
        if (!tableName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
        for (var field : fieldNameList) {
            if (!field.matches("^[a-zA-Z0-9_]+$")) {
                throw new IllegalArgumentException("Invalid field name: " + field);
            }
        }

        // Example:
        // select 
        //     jsonb_build_object('1', xmlrecord->'1', '3', xmlrecord->'3') AS xmlrecord
        // from test.test1 where recid = 'b'

        System.out.println("Step 1");
        StringBuilder fieldListBuilder = new StringBuilder();
        fieldListBuilder.append("jsonb_build_object(");
        for (int i = 0; i < fieldNameList.size(); i++) {
            if (i > 0) {
                fieldListBuilder.append(", ");
            }
            fieldListBuilder.append("'").append(fieldNameList.get(i)).append("', xmlrecord->'").append(fieldNameList.get(i)).append("'");
        }
        fieldListBuilder.append(") as xmlrecord");

        System.out.println("Step 2: " + fieldListBuilder.toString());
        try (
            final var conn = getConnection(); 
            final var stmt = conn.prepareStatement(String.format(SQL_SELECT_XML_BY_RECID_WITH_FIELDS, fieldListBuilder.toString(), tableName))
        ) {
            stmt.setString(1, recid);
            try (final var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Step 3: " + rs.getString(1));
                    var jsonObj = objectMapper.readTree(rs.getString(1));
                    System.out.println("Step 3 done");
                    return new T24CoreRecordEntity(recid, jsonObj);
                }
            }
            System.out.println("Step 4");
            return new T24CoreRecordEntity(); // Replace with actual query and mapping logic
        } catch (Exception e) {
            System.err.println("Error occurred while fetching record by recid: " + recid + " from table: " + tableName + ". Error: " + e);
            throw new RuntimeException(e);
        }
    }
}
