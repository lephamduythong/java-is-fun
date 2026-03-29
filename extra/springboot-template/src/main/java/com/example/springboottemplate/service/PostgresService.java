package com.example.springboottemplate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class PostgresService {

    @Value("${postgres.jndi-name}")
    private String jndiName;

    private DataSource dataSource;

    @PostConstruct
    public void init() throws NamingException {
        dataSource = (DataSource) new InitialContext().lookup(jndiName);
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
