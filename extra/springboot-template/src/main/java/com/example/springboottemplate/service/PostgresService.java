package com.example.springboottemplate.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresService {

    private static final String PROPERTIES_FILE = "/application.properties";
    private static final String JNDI_KEY = "postgres.jndi-name";

    private static volatile PostgresService instance;

    private final DataSource dataSource;

    private PostgresService() throws NamingException, IOException {
        Properties props = new Properties();
        try (InputStream is = PostgresService.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (is == null) {
                throw new IOException("Cannot find " + PROPERTIES_FILE);
            }
            props.load(is);
        }
        String jndiName = props.getProperty(JNDI_KEY);
        dataSource = (DataSource) new InitialContext().lookup(jndiName);
    }

    public static PostgresService getInstance() throws NamingException, IOException {
        if (instance == null) {
            synchronized (PostgresService.class) {
                if (instance == null) {
                    instance = new PostgresService();
                }
            }
        }
        return instance;
    }

    public String getXmlRecord(String recid) throws SQLException {
        String sql = "SELECT xmlrecord::text FROM test1 WHERE recid = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, recid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
                return null;
            }
        }
    }
}
