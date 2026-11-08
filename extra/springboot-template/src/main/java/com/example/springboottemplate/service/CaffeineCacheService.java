package com.example.springboottemplate.service;

import com.example.springboottemplate.pool.RequestThreadPoolManager;
import com.example.springboottemplate.repository.PostgresRecordRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CaffeineCacheService {

    private static class Holder {
        private static final CaffeineCacheService INSTANCE = new CaffeineCacheService();
    }

    public static CaffeineCacheService getInstance() {
        return Holder.INSTANCE;
    }

    private final PostgresRecordRepository recordRepository;
    private final LoadingCache<String, Optional<String>> recordCache;

    private CaffeineCacheService() {
        this.recordRepository = new PostgresRecordRepository();
        this.recordCache = Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                // .recordStats() // enable metric
                .build(recid -> {
                    String value = recordRepository.findXmlRecordById(recid);
                    if (value != null) {
                        System.out.println("Cache miss for recid: " + recid);
                    }
                    return Optional.ofNullable(value);
                });
    }

    public String getXmlRecord(String recid) {
        Optional<String> cachedValue = recordCache.getIfPresent(recid);
        if (cachedValue != null && cachedValue.isPresent()) {
            System.out.println("Cache hit for recid: " + recid);
            return cachedValue.get();
        }

        Optional<String> value = recordCache.get(recid);
        if (!value.isPresent()) {
            recordCache.invalidate(recid);
        }

        return value.orElse(null);
    }
}
