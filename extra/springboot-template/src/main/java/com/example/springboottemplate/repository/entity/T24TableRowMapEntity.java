package com.example.springboottemplate.repository.entity;

public class T24TableRowMapEntity {
    private String selectTable;
    private String selectColumn;
    private String viewName;
    private String viewColumn;

    public T24TableRowMapEntity() {
    }

    public T24TableRowMapEntity(String selectTable, String selectColumn, String viewName, String viewColumn) {
        this.selectTable = selectTable;
        this.selectColumn = selectColumn;
        this.viewName = viewName;
        this.viewColumn = viewColumn;
    }

    public String getSelectTable() {
        return selectTable;
    }
    public void setSelectTable(String selectTable) {
        this.selectTable = selectTable;
    }
    public String getSelectColumn() {
        return selectColumn;
    }
    public void setSelectColumn(String selectColumn) {
        this.selectColumn = selectColumn;
    }
    public String getViewName() {
        return viewName;
    }
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    public String getViewColumn() {
        return viewColumn;
    }
    public void setViewColumn(String viewColumn) {
        this.viewColumn = viewColumn;
    }
}
