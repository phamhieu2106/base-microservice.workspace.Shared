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
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
    private final StringRedisTemplate redisTemplate;

    public <T> void storeKey(String key, Supplier<T> supplier) {
        Boolean hasKey = redisTemplate.hasKey(key);
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

    public <T> void storeKey(String key, long timeLock, Supplier<T> supplier) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(hasKey)) {
            supplier.get();
            return;
        }
        try {
            lock(key, timeLock, supplier);
        } finally {
            try {
                unlock(key);
            } catch (Exception e) {
                log.error(">>>> Try to unlock with time lock func error: {} ", e.getMessage());
            }
        }
        supplier.get();
    }

    private <T> void lock(String key, long timeLock, Supplier<T> supplier) {
        try {
            redisTemplate.opsForValue().set(key, supplier.toString(), timeLock, TimeUnit.SECONDS);
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
