package com.example.springboottemplate.repository;
import com.example.springboottemplate.repository.intf.T24CoreRepository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;

public class T24CoreRepositoryImpl implements T24CoreRepository {
    private String T24_DB_JNDI_NAME = "java:jboss/datasources/PostgresDS";
    private String SQL_SELECT_XML_BY_RECID = "SELECT xmlrecord::text FROM test.%s WHERE recid = ?";

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
                    return new T24CoreRecordEntity(recid, rs.getString(1));
                }
            }
            return new T24CoreRecordEntity(); // Replace with actual query and mapping logic
        } catch (Exception e) {
            System.err.println("Error occurred while fetching record by recid: " + recid + " from table: " + tableName + ". Error: " + e);
            throw new RuntimeException(e);
        }
    }
}
