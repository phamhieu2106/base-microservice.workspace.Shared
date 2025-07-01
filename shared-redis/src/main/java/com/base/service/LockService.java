package com.base.service;

import com.base.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class LockService {

    private final CacheUtils cacheUtils;

    @Autowired
    public LockService(@Lazy CacheUtils cacheUtils) {
        this.cacheUtils = cacheUtils;
    }

    public <T> void lock(String key, Supplier<T> supplier) {
        cacheUtils.storeToLockKey(key, supplier);
    }

    public <T> void lock(String key, long timeout, Supplier<T> supplier) {
        cacheUtils.storeToLockKey(key, timeout, supplier);
    }
}
