package com.example.springboottemplate.repository.intf;

import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;

public interface T24CoreRepository {
    T24CoreRecordEntity getRecordByRecid(String tableName, String recid);
}
