package com.example.springboottemplate.repository.intf;

import java.util.List;
import java.util.Set;

import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;

public interface T24CoreRepository {
    T24CoreRecordEntity getRecordByRecid(String tableName, String recid);
    T24CoreRecordEntity getRecordByRecid(String tableName, List<String> fieldNameList, String recid);
    T24CoreRecordEntity getRecordByRecidV2(String viewName, Set<String> fieldNameList, String recid);
}
