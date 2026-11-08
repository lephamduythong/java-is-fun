package com.example.springboottemplate.repository.intf;

import java.util.List;

import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;

public interface T24CoreRepository {
    T24CoreRecordEntity getRecordByRecid(String tableName, String recid);
    T24CoreRecordEntity getRecordByRecid(String tableName, List<String> fieldNameList, String recid);
}
