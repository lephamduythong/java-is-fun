package com.example.springboottemplate.repository;

import com.example.springboottemplate.service.PostgresService;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public class PostgresRecordRepository {

    public String findXmlRecordById(String recid) {
        try {
            return PostgresService.getInstance().getXmlRecord(recid);
        } catch (NamingException | IOException e) {
            throw new IllegalStateException("Failed to initialize PostgreSQL access", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to read record from PostgreSQL", e);
        }
    }
}
