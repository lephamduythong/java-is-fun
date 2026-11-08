package com.example.springboottemplate.repository.debug;

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
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;

public class PostgresqlDebugMain {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String T24_DB_JNDI_NAME = "java:jboss/datasources/PostgresDS";

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USERNAME = "t24";
    private static final String PASSWORD = "123";

    public static void main(String[] args) throws Exception {
        // var result = getRecordByRecidV2(
        //     "V_FBNK_COLLATERAL_RIGHT" 
        //     ,Set.of("LIMIT.REFERENCE", "COLLATERAL.CODE") 
        //     ,"COR23080THP3X");
        // System.out.println(result);


        var stopwatch = new Stopwatch();
        try (var conn = getConnection()) {
            var sql = "select recid, orclfilename as table_name, 'V_' || orclfilename as view_name from tafj_voc where recid like 'F.%' or recid like 'FBNK.%'";
            var tafjVoc = queryDB(conn, sql);
            System.out.println(tafjVoc);
            System.out.println("Elapsed time: " + stopwatch.elapsed() + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static T24CoreRecordEntity getRecordByRecidV2(String viewName, Set<String> fieldNameSet, String recid) {
        
        // GUARD 1: Validate table name and field names to prevent SQL injection
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
            var stmt = conn.prepareStatement(metaDataFieldSQL);
            var resultSet = stmt.executeQuery();

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

            // Example Set: [MPM_CUST, VALIDITY_DATE, LIMIT_REF_CUST_4, LIMIT_CAP_PERC_8, CO_CODE, RESERVED_1, RESERVED_6, RESERVED_5, CO_PRIORITY, STATUS, CUSTOMER_17, TM_NOMINAL_VAL, AUTHORISER, OVERRIDE_26, LIMIT_REF_CUST, INTERNAL, CUSTOMER, COLLATERAL_ID_23, CURR_NO, RELATION_ALLOW, AUDIT_DATE_TIME, PERCENT_ALLOC_5, INPUTTER, DEPT_CODE, ALLOCATION_AMT, CUSTOMER_ID, DATE_TIME_30, THE_RECORD, LIMIT_ID, OS_PERCENT_COVER, COMPANY, REVIEW_DATE_FQU, NOTARY, MPM_INSURANCE, MPM_CUS_ROLE, COLLATERAL_RIGHT, COMPANY_2, LIMIT_REFERENCE, MPM_CUST_18, RESERVED_6_10, PERCENTAGE_COVER, DATE_TIME, MPM_CUS_ROLE_18, PERCENT_ALLOC, COLLATERAL_CODE, RECORD_STATUS, EXPIRY_DATE, LIMIT_ID_20, NOTES, SEQ_NO, ALLOCATION_AMT_7, LIMIT_CAP_PERC, CA_PROLENDER_ID, recid, ALLOC_WORK_ID, INSTITUTION, MPM_NO, COLLATERAL_ID, AUDITOR_CODE, MPM_FLAG, LIMIT_REFERENCE_3, NOTES_16, OVERRIDE, REG_LIMIT, ALLOCATION_CCY, RESERVED_5_9, INPUTTER_29, MPM_TRFR_NO, ALLOCATION_CCY_6]
            // Lọc ra các cột có số ở cuối thành 1 set
            var columnsWithNumbers = columnSet.stream()
                    .filter(column -> column.matches(".*_\\d+$"))
                    .collect(Collectors.toSet());
            // System.out.println("Columns with numbers at the end: " + columnsWithNumbers);

            // Trong tập columnsWithNumbers, ví dụ với item "LIMIT_REF_CUST_4" thì lấy ra string "LIMIT_REF_CUST" lưu thành 1 set "LIMIT_REF_CUST", ...
            var columnsWithoutNumbers = columnsWithNumbers.stream()
                    .map(column -> column.replaceAll("_\\d+$", ""))
                    .collect(Collectors.toSet());
            // System.out.println(columnsWithoutNumbers);
            
            // Lấy tập columnSet trừ đi columnsWithoutNumbers
            var finalColumnSet = columnSet.stream()
                    .filter(column -> !columnsWithoutNumbers.contains(column))
                    .collect(Collectors.toSet());
            
            System.out.println("finalColumnSet");
            System.out.println(finalColumnSet);

            // Lưu finalColumnSet vào cache
            // ...

            final Set<String> transformedFieldNameList = fieldNameSet.stream()
                    .map(field -> field.replace(".", "_"))
                    .collect(Collectors.toSet());
            var selectFields = finalColumnSet.stream()
                    .filter(field -> transformedFieldNameList.contains(field) 
                        || transformedFieldNameList.contains(field.replaceAll("_\\d+$", "")))
                    .collect(Collectors.toSet());

            System.out.println("selectFields");
            System.out.println(selectFields);

            var viewNameWrapped = "\"" + viewName + "\"";
            var selectSql = "select jsonb_build_object(" + "{selectFields}" +") as xmlrecord " +
                    "from " + viewNameWrapped + " where recid = '" + recid + "';";
            System.out.println(selectSql);
            
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
            // GUARD 2 with JsqlParser: ensure the SQL is a SELECT statement
            var statement = CCJSqlParserUtil.parse(selectSql);
            if (!(statement instanceof Select)) {
                throw new IllegalArgumentException("The SQL statement is not a SELECT statement");
            }

            var result = queryDB(conn, selectSql);

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

    private static Connection getConnection() throws SQLException, NamingException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
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
