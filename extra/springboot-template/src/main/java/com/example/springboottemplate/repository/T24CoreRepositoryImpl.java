package com.example.springboottemplate.repository;
import com.example.springboottemplate.repository.intf.T24CoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.springboottemplate.repository.debug.Stopwatch;
import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;

public class T24CoreRepositoryImpl implements T24CoreRepository {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    private String T24_DB_JNDI_NAME = "java:jboss/datasources/PostgresDS";
    private String SQL_SELECT_XML_BY_RECID = "SELECT xmlrecord FROM test.%s WHERE recid = ?";
    private String SQL_SELECT_XML_BY_RECID_WITH_FIELDS = "SELECT %s FROM test.%s WHERE recid = ?";

    // private Connection getConnection() throws SQLException, NamingException {
    //     return ((DataSource) new InitialContext().lookup(T24_DB_JNDI_NAME)).getConnection();
    // }

    private static final String JDBC_URL = "jdbc:postgresql://aws-sg-cbs-sit-rds-aurora-postgresql-002.cluster-cn6lmdft73t3.ap-southeast-1.rds.amazonaws.com:5432/transact_sit";
    private static final String USERNAME = "t24";
    private static final String PASSWORD = "zS7ec$tvT^a#xS(4";

    private static Connection getConnection() throws SQLException, NamingException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
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
            return null;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching record by recid: " + recid + " from table: " + tableName + ". Error: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T24CoreRecordEntity getRecordByRecidV2(String viewName, Set<String> fieldNameSet, String recid) {
        
        // GUARD: Validate table name and field names to prevent SQL injection
        // Check basic SQL injection prevention for table name and field names
        if (!viewName.matches("^[a-zA-Z0-9_.#]+$")) {
            throw new IllegalArgumentException("Invalid table name: " + viewName);
        }
        for (var field : fieldNameSet) {
            if (!field.matches("^[a-zA-Z0-9_.#]+$")) {
                throw new IllegalArgumentException("Invalid field name: " + field);
            }
        }
        // Check existing table in tafj_voc (cached)

        // Check existing fields in STANDARD.SELECTION (cached)


        // START TO GET DATA
        var metaDataFieldSQL = "SELECT \r\n" + //
                        "    column_name \r\n" + //
                        "    ,data_type \r\n" + //
                        "--    ,is_nullable\r\n" + //
                        "FROM \r\n" + //
                        "    information_schema.columns\r\n" + //
                        "WHERE \r\n" + //
                        "    table_schema = 'public'\r\n" + //
                        "--    AND data_type = 'jsonb'\r\n" + //
                        "    AND table_name = '" + viewName + "' -- Thay bằng tên view cần xem";
        var columnSet = new HashSet<String>();
        
        try (var conn = getConnection())
        {   

            Stopwatch stopwatch = new Stopwatch();
            
            var stmt = conn.prepareStatement(metaDataFieldSQL);
            var resultSet = stmt.executeQuery();
            System.out.println("Elapsed time after executing metaDataFieldSQL: " + stopwatch.elapsed() + " ms");

            ResultSetMetaData metadata = resultSet.getMetaData();
            int columnCount = metadata.getColumnCount();
            
            for (int column = 1; column <= columnCount; column++) {
                if (column > 1) {
                    System.out.print("\t");
                }
                System.out.print(metadata.getColumnLabel(column));
            }

            while (resultSet.next()) {
                columnSet.add(resultSet.getString(1));
            }


            
            System.out.println("Elapsed time after processing resultSet: " + stopwatch.elapsed() + " ms");

            // Example Set: [MPM_CUST, VALIDITY_DATE, LIMIT_REF_CUST_4, LIMIT_CAP_PERC_8, CO_CODE, RESERVED_1, RESERVED_6, RESERVED_5, CO_PRIORITY, STATUS, CUSTOMER_17, TM_NOMINAL_VAL, AUTHORISER, OVERRIDE_26, LIMIT_REF_CUST, INTERNAL, CUSTOMER, COLLATERAL_ID_23, CURR_NO, RELATION_ALLOW, AUDIT_DATE_TIME, PERCENT_ALLOC_5, INPUTTER, DEPT_CODE, ALLOCATION_AMT, CUSTOMER_ID, DATE_TIME_30, THE_RECORD, LIMIT_ID, OS_PERCENT_COVER, COMPANY, REVIEW_DATE_FQU, NOTARY, MPM_INSURANCE, MPM_CUS_ROLE, COLLATERAL_RIGHT, COMPANY_2, LIMIT_REFERENCE, MPM_CUST_18, RESERVED_6_10, PERCENTAGE_COVER, DATE_TIME, MPM_CUS_ROLE_18, PERCENT_ALLOC, COLLATERAL_CODE, RECORD_STATUS, EXPIRY_DATE, LIMIT_ID_20, NOTES, SEQ_NO, ALLOCATION_AMT_7, LIMIT_CAP_PERC, CA_PROLENDER_ID, recid, ALLOC_WORK_ID, INSTITUTION, MPM_NO, COLLATERAL_ID, AUDITOR_CODE, MPM_FLAG, LIMIT_REFERENCE_3, NOTES_16, OVERRIDE, REG_LIMIT, ALLOCATION_CCY, RESERVED_5_9, INPUTTER_29, MPM_TRFR_NO, ALLOCATION_CCY_6]
            // Lọc ra các cột có số ở cuối thành 1 set
            var columnsWithNumbers = columnSet.parallelStream()
                    .filter(column -> column.matches(".*_\\d+$"))
                    .collect(Collectors.toSet());
            // System.out.println("Columns with numbers at the end: " + columnsWithNumbers);

            // Trong tập columnsWithNumbers, ví dụ với item "LIMIT_REF_CUST_4" thì lấy ra string "LIMIT_REF_CUST" lưu thành 1 set "LIMIT_REF_CUST", ...
            var columnsWithoutNumbers = columnsWithNumbers.parallelStream()
                    .map(column -> column.replaceAll("_\\d+$", ""))
                    .collect(Collectors.toSet());
            // System.out.println(columnsWithoutNumbers);
            
            // Lấy tập columnSet trừ đi columnsWithoutNumbers
            var finalColumnSet = columnSet.parallelStream()
                    .filter(column -> !columnsWithoutNumbers.contains(column))
                    .collect(Collectors.toSet());
            
            System.out.println("finalColumnSet");
            System.out.println(finalColumnSet);

            // Lưu finalColumnSet vào cache
            // ...

            final Set<String> transformedFieldNameList = fieldNameSet.parallelStream()
                    .map(field -> field.replace(".", "_"))
                    .collect(Collectors.toSet());
            var selectFields = finalColumnSet.parallelStream()
                    .filter(field -> transformedFieldNameList.contains(field) 
                        || transformedFieldNameList.contains(field.replaceAll("_\\d+$", "")))
                    .collect(Collectors.toSet());

            System.out.println("selectFields");
            System.out.println(selectFields);

            var viewNameWrapped = "\"" + viewName + "\"";
            var selectSql = "select jsonb_build_object(" + "{selectFields}" +") as xmlrecord " +
                    "from " + viewNameWrapped + " where recid = '" + recid + "';";
            System.out.println(selectSql);

            System.out.println("Elapsed time before generating selectFieldsString: " + stopwatch.elapsed() + " ms");
            
            // Example select sql:
            // selectFields set: [COLLATERAL_CODE, LIMIT_REFERENCE_3]
            // select
            //     jsonb_build_object(
            //         'COLLATERAL.CODE', "COLLATERAL_CODE",
            //         'LIMIT.REFERENCE', "LIMIT_REFERENCE_3"
            //     ) as xmlrecord
            // from "V_FBNK_COLLATERAL_RIGHT" where recid = 'COR23080THP3X';

            // Generate the selectFields string for the SQL query
            var selectFieldsString = selectFields.stream()
                    .map(field -> "'"  
                        + field.replaceAll("_\\d+$", "").replace("_", ".")
                        + "', \"" + field + "\"")
                    .collect(Collectors.joining(", "));
            selectSql = selectSql.replace("{selectFields}", selectFieldsString);

            System.out.println(selectSql);
            var result = queryDB(conn, selectSql);

            System.out.println("Elapsed time after querying DB: " + stopwatch.elapsed() + " ms");

            System.out.println("result: " + result);

            var jsonVal = result.isEmpty() ? null 
                : objectMapper.readTree(result.get(0).get("XMLRECORD"));

            // System.out.println("jsonVal: " + jsonVal.get("COLLATERAL.CODE"));

            return new T24CoreRecordEntity(recid, jsonVal);
        } 
        catch (Exception e) {
            System.err.println("SQLException occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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
}
