package com.example.springboottemplate.service;

import com.example.springboottemplate.WonderUtils;
import com.example.springboottemplate.repository.debug.Stopwatch;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

public class SysCacheService {
    // Define singleton instance using the Holder pattern
    private static class Holder {
        private static final SysCacheService INSTANCE = new SysCacheService();
    }
    public static SysCacheService getInstance() {
        return Holder.INSTANCE;
    }

    private static final String CACHE_KEY = "tafjvoc";
    private final LoadingCache<String, HashMap<String, HashMap<String, String>>> tafjVocCache;

    private SysCacheService() {
        tafjVocCache = Caffeine.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(1, TimeUnit.HOURS)
            // .recordStats() // enable metric
            .build(cacheKey -> {
                var stopwatch = new Stopwatch();
                try (var conn = getConnection()) {
                    var sql = "select recid, orclfilename as table_name, 'V_' || orclfilename as view_name from tafj_voc where recid like 'F.%' or recid like 'FBNK.%'";
                    var tafjVoc = queryDB(conn, sql);

                    // Index value
                    var indexValueMap = new HashMap<String, HashMap<String, String>>();
                    tafjVoc.forEach(i -> {
                        var recid = i.get("VIEW_NAME");
                        indexValueMap.put(recid, i);
                    });
                    
                    System.out.println("Elapsed time: " + stopwatch.elapsed() + " ms");

                    return indexValueMap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new HashMap<>();
            });
    }

    public HashMap<String, HashMap<String, String>> getTafjVocCache() {
        return tafjVocCache.get(CACHE_KEY);
    }

    // Inject dependencies here if needed
    // private final DataRepository _dataRepository = DataRepository.getInstance();
    // private final CacheService _cacheService = CacheService.getInstance();

    private static final String JDBC_URL = "jdbc:postgresql://aws-sg-cbs-sit-rds-aurora-postgresql-002.cluster-cn6lmdft73t3.ap-southeast-1.rds.amazonaws.com:5432/transact_sit";
    private static final String USERNAME = "t24";
    private static final String PASSWORD = "zS7ec$tvT^a#xS(4";

    private static List<HashMap<String, String>> queryDB(Connection connection, String sql)
            throws SQLException {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL cannot be null or empty");
        }

        final var result = new ArrayList<HashMap<String, String>>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Lấy tên cột/alias 1 lần duy nhất, tránh gọi lại metadata mỗi row
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                // getColumnLabel trả về alias (sau AS) nếu có, không thì trả về tên cột gốc
                columnNames[i] = meta.getColumnLabel(i + 1)
                    .toUpperCase(); // Chuẩn hóa cho mọi DB, xử lun cho lowercase trong postgresql
            }

            while (rs.next()) {
                final var item = new HashMap<String, String>();
                for (int i = 0; i < columnCount; i++) {
                    item.put(columnNames[i], rs.getString(i + 1));
                }
                result.add(item);
            }
        }

        return result;
    }

    private static Connection getConnection() throws SQLException, NamingException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
