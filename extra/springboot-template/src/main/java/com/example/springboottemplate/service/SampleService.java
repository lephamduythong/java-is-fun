package com.example.springboottemplate.service;

public class SampleService {
    // Define singleton instance using the Holder pattern
    private static class Holder {
        private static final SampleService INSTANCE = new SampleService();
    }
    public static SampleService getInstance() {
        return Holder.INSTANCE;
    }

    // Inject dependencies here if needed
    // private final DataRepository _dataRepository = DataRepository.getInstance();
    // private final CacheService _cacheService = CacheService.getInstance();

    public String method1() {
        // Processing...
        return "Hello from method1";
    }
}
