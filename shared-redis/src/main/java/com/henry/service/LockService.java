package com.henry.service;

import com.henry.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class LockService {

    private final RedisUtils redisUtils;

    @Autowired
    public LockService(@Lazy RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public <T> void lock(String key, Supplier<T> supplier) {
        redisUtils.storeKey(key, supplier);
    }

    public <T> void lock(String key, long timeout, Supplier<T> supplier) {
        redisUtils.storeKey(key, timeout, supplier);
    }
}
