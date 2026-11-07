package com.example.springboottemplate.service;

import com.example.springboottemplate.repository.PostgresRecordRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineCacheService {

    private static class Holder {
        private static final CaffeineCacheService INSTANCE = new CaffeineCacheService();
    }

    public static CaffeineCacheService getInstance() {
        return Holder.INSTANCE;
    }

    private final PostgresRecordRepository recordRepository;
    private final Cache<String, String> recordCache;

    private CaffeineCacheService() {
        this.recordRepository = new PostgresRecordRepository();
        this.recordCache = Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                // .recordStats() // enable metric
                .build();
    }

    public String getXmlRecord(String recid) {
        String cachedValue = recordCache.getIfPresent(recid);
        if (cachedValue != null) {
            System.out.println("Cache hit for recid: " + recid);
            return cachedValue;
        }

        String value = recordRepository.findXmlRecordById(recid);
        if (value != null) {
            System.out.println("Cache miss for recid: " + recid);
            recordCache.put(recid, value);
        }
        
        return value;
    }
}
