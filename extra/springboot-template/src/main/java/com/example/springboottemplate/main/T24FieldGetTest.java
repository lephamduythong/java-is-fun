package com.example.springboottemplate.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.springboottemplate.repository.debug.Stopwatch;
import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;
import com.example.springboottemplate.repository.entity.T24TableRowMapEntity;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;

public class T24FieldGetTest {
    
    private static Set<String> t24TableSet = new HashSet<String>();
    private static Map<String, T24TableRowMapEntity> t24TableFieldMap = new HashMap<>();
    
    public static void main(String[] args) {        
        // COLLATERAL	ADDRESS	V_FBNK_COLLATERAL	ADDRESS_18	D_F_COLLATERAL
        // COLLATERAL	APPLICATION	V_FBNK_COLLATERAL	APPLICATION	D_F_COLLATERAL
        // COLLATERAL	APPLICATION.ID	V_FBNK_COLLATERAL	APPLICATION_ID	D_F_COLLATERAL
        // COLLATERAL	ASSET.CAP	V_FBNK_COLLATERAL	ASSET_CAP_6	D_F_COLLATERAL

        t24TableSet.add("COLLATERAL");

        var ent1 = new T24TableRowMapEntity("COLLATERAL", "ADDRESS", "V_FBNK_COLLATERAL", "ADDRESS_18", "D_F_COLLATERAL");
        var ent2 = new T24TableRowMapEntity("COLLATERAL", "APPLICATION", "V_FBNK_COLLATERAL", "APPLICATION", "D_F_COLLATERAL");
        var ent3 = new T24TableRowMapEntity("COLLATERAL", "APPLICATION.ID", "V_FBNK_COLLATERAL", "APPLICATION_ID", "D_F_COLLATERAL");
        var ent4 = new T24TableRowMapEntity("COLLATERAL", "ASSET.CAP", "V_FBNK_COLLATERAL", "ASSET_CAP_6", "D_F_COLLATERAL");
        
        t24TableFieldMap.put("COLLATERAL,ADDRESS", ent1);
        t24TableFieldMap.put("COLLATERAL,APPLICATION", ent2);
        t24TableFieldMap.put("COLLATERAL,APPLICATION.ID", ent3);
        t24TableFieldMap.put("COLLATERAL,ASSET.CAP", ent4);
        
        getRecord("COLLATERAL", List.of("CO23080SYH39"), List.of("ADDRESS", "APPLICATION", "APPLICATION.ID", "ASSET.CAP"));
    }

    private static void getRecord(String table, List<String> recid, List<String> fieldList) {
        
        Stopwatch sw = new Stopwatch();

        // Check table, recid, fieldList input data. Pattern: only contains [A-Za-z0-9.$]
        if (table == null || !table.matches("[A-Za-z0-9.$]+")) {
            throw new IllegalArgumentException("Invalid table: " + table);
        }
        if (recid == null || recid.isEmpty()) {
            throw new IllegalArgumentException("Invalid recid: " + recid);
        }
        for (String id : recid) {
            if (id == null || !id.matches("[A-Za-z0-9.$]+")) {
                throw new IllegalArgumentException("Invalid recid: " + id);
            }
        }
        if (fieldList == null) {
            throw new IllegalArgumentException("Invalid fieldList: " + fieldList);
        }
        for (String field : fieldList) {
            if (field == null || !field.matches("[A-Za-z0-9.$]+")) {
                throw new IllegalArgumentException("Invalid field: " + field);
            }
        }
 
        // Check if table exists
        if (!t24TableSet.contains(table)) {
            throw new IllegalArgumentException("Unknown table: " + table);
        }

        // Check if fields exist in the table
        for (String field : fieldList) {
            if (!t24TableFieldMap.containsKey(table + "," + field)) {
                throw new IllegalArgumentException("Unknown field: " + field + " in table: " + table);
            }
        }

        // Get real view name
        var viewName = t24TableFieldMap.get(table + "," + fieldList.get(0)).getViewName();

        // Get field mappings
        var fieldMappings = new ArrayList<String>();
        for (String field : fieldList) {
            var mapping = t24TableFieldMap.get(table + "," + field);
            if (mapping == null) {
                throw new IllegalArgumentException("Unknown field: " + field + " in table: " + table);
            }
            fieldMappings.add(wrapInDoubleQuote(mapping.getViewColumn()));
        }
        
        // Build SQL query
        var sql = new StringBuilder("select recid, ")
            .append(String.join(", ", fieldMappings))
            .append(" from \"")
            .append(viewName)
            .append("\" where recid = any(array['")
            .append(String.join("','", recid))
            .append("'])")
            .toString();
    
        // Guard SQL statement check
        // try {
        //     var statement = CCJSqlParserUtil.parse(sql);
        //     if (!(statement instanceof Select)) {
        //         throw new IllegalArgumentException("The SQL statement is not a SELECT statement");
        //     }
        // } catch (JSQLParserException e) {
        //     throw new IllegalArgumentException("Error when parsing SQL statement: " + sql, e);
        // }

        System.out.println("Elapsed time: " + sw.elapsed() + " ms");

        System.out.println(sql);
    }

    private static String wrapInDoubleQuote(String value) {
        return "\"" + value + "\"";
    }
}
