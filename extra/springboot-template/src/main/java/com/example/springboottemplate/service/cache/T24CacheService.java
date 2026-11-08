package com.example.springboottemplate.service.cache;

import com.example.springboottemplate.repository.PostgresRecordRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class T24CacheService {
    private static class Holder {
        private static final T24CacheService INSTANCE = new T24CacheService();
    }
    public static T24CacheService getInstance() {
        return Holder.INSTANCE;
    }
}
