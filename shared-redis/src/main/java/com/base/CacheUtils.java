package com.base;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class CacheUtils {

    private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);
    private final StringRedisTemplate redisTemplate;

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void store(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void storeToLockKey(String key, Supplier<T> supplier) {
        Boolean hasKey = exists(key);
        if (Boolean.TRUE.equals(hasKey)) {
            supplier.get();
            return;
        }
        try {
            lock(key, 30L, supplier);
        } finally {
            try {
                unlock(key);
            } catch (Exception e) {
                log.error(">>>> Try to unlock default lock func error: {} ", e.getMessage());
            }
        }
        supplier.get();
    }

    public <T> void storeToLockKey(String key, long timeStore, Supplier<T> supplier) {
        Boolean hasKey = exists(key);
        if (Boolean.TRUE.equals(hasKey)) {
            supplier.get();
            return;
        }
        try {
            lock(key, timeStore, supplier);
        } finally {
            try {
                unlock(key);
            } catch (Exception e) {
                log.error(">>>> Try to unlock with time lock func error: {} ", e.getMessage());
            }
        }
        supplier.get();
    }

    private <T> void lock(String key, long timeStore, Supplier<T> supplier) {
        try {
            redisTemplate.opsForValue().set(key, supplier.toString(), timeStore, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(">>>> Try to lock error: {}", e.getMessage());
        }
    }

    private void unlock(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
