package com.example.springboottemplate.service;
import com.example.springboottemplate.repository.intf.T24CoreRepository;
import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;

public class T24DBService {
    private final T24CoreRepository t24CoreRepository;

    public T24DBService(T24CoreRepository t24CoreRepository) {
        this.t24CoreRepository = t24CoreRepository;
    }

    public T24CoreRecordEntity getRecordByRecid(String tableName, String recid) {
        return t24CoreRepository.getRecordByRecid(tableName, recid);
    }
}
