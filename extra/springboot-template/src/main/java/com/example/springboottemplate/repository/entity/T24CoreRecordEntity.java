package com.example.springboottemplate.repository.entity;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class T24CoreRecordEntity {
    private String recid;
    private JsonNode xmlrecord;

    public T24CoreRecordEntity() {
    }

    public T24CoreRecordEntity(String recid, JsonNode xmlrecord) {
        this.recid = recid;
        this.xmlrecord = xmlrecord;
    }

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public JsonNode getXmlrecord() {
        return xmlrecord;
    }

    public void setXmlrecord(JsonNode xmlrecord) {
        this.xmlrecord = xmlrecord;
    }
}
