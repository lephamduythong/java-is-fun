package com.example.springboottemplate.service;
import com.example.springboottemplate.repository.intf.T24CoreRepository;
import com.example.springboottemplate.repository.entity.T24CoreRecordEntity;
import java.util.List;
import java.util.Set;

public class T24DBService {
    private final T24CoreRepository t24CoreRepository;

    public T24DBService(T24CoreRepository t24CoreRepository) {
        this.t24CoreRepository = t24CoreRepository;
    }

    public T24CoreRecordEntity getRecordByRecid(String tableName, String recid) {
        return t24CoreRepository.getRecordByRecid(tableName, recid);
    }
    
    public T24CoreRecordEntity getRecordByRecid(String tableName, List<String> fieldNameList, String recid) {
        return t24CoreRepository.getRecordByRecid(tableName, fieldNameList, recid);
    }

    public T24CoreRecordEntity getRecordByRecidV2(String viewName, Set<String> fieldNameList, String recid) {
        return t24CoreRepository.getRecordByRecidV2(viewName, fieldNameList, recid);
    }

}
