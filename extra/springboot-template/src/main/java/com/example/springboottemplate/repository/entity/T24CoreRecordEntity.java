package com.example.springboottemplate.repository.entity;

public class T24CoreRecordEntity {
    private String recid;
    private String xmlrecord;

    public T24CoreRecordEntity() {
    }

    public T24CoreRecordEntity(String recid, String xmlrecord) {
        this.recid = recid;
        this.xmlrecord = xmlrecord;
    }

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getXmlrecord() {
        return xmlrecord;
    }

    public void setXmlrecord(String xmlrecord) {
        this.xmlrecord = xmlrecord;
    }
}
