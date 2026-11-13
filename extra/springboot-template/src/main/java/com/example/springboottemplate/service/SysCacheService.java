package com.example.springboottemplate.service;

import com.example.springboottemplate.WonderUtils;
import com.example.springboottemplate.repository.debug.Stopwatch;
import com.example.springboottemplate.repository.entity.T24TableRowMapEntity;
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
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.naming.NamingException;

public class SysCacheService {
    // Define singleton instance using the Holder pattern
    private static class Holder {
        private static final SysCacheService INSTANCE = new SysCacheService();
    }
    public static SysCacheService getInstance() {
        return Holder.INSTANCE;
    }

    // private static final String CACHE_KEY = "tafjvoc";
    private static final String CACHE_KEY_T24_TABLE_ROW_MAP = "t24tablerowmap";
    // private final LoadingCache<String, HashMap<String, HashMap<String, String>>> tafjVocCache;
    private final LoadingCache<String, Map<String, T24TableRowMapEntity>> t24TableRowMapCache; // Ex: key (EB.CITAD.TRANSACTION,TRANSACTION.TYPE) val (V_FBNK_EB_CITAD_TRANSACTION	TRANSACTION_TYPE)

    private SysCacheService() {
        // tafjVocCache = Caffeine.newBuilder()
        //     .maximumSize(1)
        //     .expireAfterWrite(1, TimeUnit.HOURS)
        //     // .recordStats() // enable metric
        //     .build(cacheKey -> buildTafjVocData());
        t24TableRowMapCache = Caffeine.newBuilder()
            .maximumSize(1)
            .refreshAfterWrite(1, TimeUnit.HOURS)   // reload nền, không block reader
            .expireAfterWrite(2, TimeUnit.HOURS)    // chặn data quá cũ nếu refresh toàn fail
            .build(cacheKey -> buildT24TableRowMapData());
    }

    private Map<String, HashMap<String, String>> buildTafjVocData() {
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
    }
    
    private Map<String, T24TableRowMapEntity> buildT24TableRowMapData() {
        try (var conn = getConnection()) {
            
            Stopwatch stopwatch = new Stopwatch();
            
            var sql = "WITH parsed_columns AS (\r\n" + //
                                "    SELECT \r\n" + //
                                "        table_name,\r\n" + //
                                "        column_name,\r\n" + //
                                "        -- Tách tên gốc (bỏ phần _số ở cuối nếu có)\r\n" + //
                                "        REGEXP_REPLACE(column_name, '_\\d+$', '') AS base_name,\r\n" + //
                                "        -- Đánh dấu: Cột có số ở cuối ưu tiên 1, cột không có số ưu tiên 0\r\n" + //
                                "        CASE WHEN column_name ~ '_\\d+$' THEN 1 ELSE 0 END AS has_number\r\n" + //
                                "    FROM \r\n" + //
                                "        information_schema.columns\r\n" + //
                                "    WHERE \r\n" + //
                                "        table_schema = 'public'\r\n" + //
                                "        AND table_name like 'V_%'\r\n" + //
                                "--        AND table_name = 'V_FBNK_COLLATERAL_RIGHT'\r\n" + //
                                "),\r\n" + //
                                "ranked_columns AS (\r\n" + //
                                "    SELECT \r\n" + //
                                "        table_name,\r\n" + //
                                "        column_name,\r\n" + //
                                "        base_name,\r\n" + //
                                "        ROW_NUMBER() OVER (\r\n" + //
                                "            PARTITION BY base_name, table_name \r\n" + //
                                "            ORDER BY has_number DESC, column_name DESC\r\n" + //
                                "        ) AS rn\r\n" + //
                                "    FROM parsed_columns\r\n" + //
                                "),\r\n" + //
                                "result as\r\n" + //
                                "(\r\n" + //
                                "\tSELECT \r\n" + //
                                "\t\t-- Cột mới: Lấy từ sau dấu _ thứ 2 và thay _ bằng dấu .\r\n" + //
                                "\t\tREPLACE(REGEXP_REPLACE(table_name, '^[^_]+_[^_]+_(.*)$', '\\1'), '_', '.') AS t24_select_table\r\n" + //
                                "\t\t-- Thay thế toàn bộ dấu _ thành .\r\n" + //
                                "\t\t,REPLACE(\r\n" + //
                                "\t        base_name, \r\n" + //
                                "\t        '_', \r\n" + //
                                "\t        '.'\r\n" + //
                                "\t    ) AS t24_select_col\r\n" + //
                                "\t    ,table_name as t24_view_name\r\n" + //
                                "\t    ,column_name AS t24_view_col\r\n" + //
                                "\tFROM ranked_columns\r\n" + //
                                "\tWHERE rn = 1\r\n" + //
                                "--\tORDER BY table_name\r\n" + //
                                ")\r\n" + //
                                "select * from result";
            
            var queryResult = queryDB(conn, sql);

            System.out.println("Elapsed time queryDB: " + stopwatch.elapsed() + " ms");

            System.out.println("Size: " + queryResult.size());
            // Index value
            // var indexValueMap = new HashMap<String, String>();
            // queryResult.forEach(i -> {
            //     indexValueMap.put(
            //         i.get("T24_SELECT_TABLE") + "," + i.get("T24_SELECT_COL"),
            //         i.get("T24_VIEW_NAME") + "," + i.get("T24_VIEW_COL") 
            //     );
            // });

            Map<String, T24TableRowMapEntity> indexValueMap = new HashMap<>();
            for (var item : queryResult) {
                var entity = new T24TableRowMapEntity();
                entity.setSelectTable(item.get("T24_SELECT_TABLE"));
                entity.setSelectColumn(item.get("T24_SELECT_COL"));
                entity.setViewName(item.get("T24_VIEW_NAME"));
                entity.setViewColumn(item.get("T24_VIEW_COL"));
                indexValueMap.put(
                    entity.getSelectTable() + "," + entity.getSelectColumn(),
                    entity
                );
            }

            
            

            // var indexValueMap = queryResult.stream()
            //     .collect(Collectors.toMap(
            //         i -> i.get("T24_SELECT_TABLE") + "," + i.get("T24_SELECT_COL"),
            //         i -> i.get("T24_VIEW_NAME") + "," + i.get("T24_VIEW_COL"),
            //         (oldVal, newVal) -> newVal
            //     ));

            WonderUtils.writeTextToFile("output/indexValueMap.txt", indexValueMap.toString());

            System.out.println("Elapsed time indexValueMap: " + stopwatch.elapsed() + " ms");

            return Map.copyOf(indexValueMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    // public HashMap<String, HashMap<String, String>> getTafjVocCache() {
    //     return tafjVocCache.get(CACHE_KEY);
    // }

    public Map<String, T24TableRowMapEntity> getT24TableRowMapCache() {
        return t24TableRowMapCache.get(CACHE_KEY_T24_TABLE_ROW_MAP);
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
