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

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void removeKey(String key) {
        redisTemplate.delete(key);
    }

    public boolean isSetMember(String key, String member) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, member));
    }

    public void addToSet(String key, String member) {
        redisTemplate.opsForSet().add(key, member);
    }

    public void storeKeyWithMinutes(String key, String value, long minutes) {
        redisTemplate.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }

    public void storeKeyWithHours(String key, String value, long hours) {
        redisTemplate.opsForValue().set(key, value, hours, TimeUnit.HOURS);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public <T> T storeToLockKey(String key, Supplier<T> supplier) {
        Boolean hasKey = exists(key);
        if (Boolean.TRUE.equals(hasKey)) {
            return supplier.get();
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
        return supplier.get();
    }

    public <T> T storeToLockKey(String key, long timeStore, Supplier<T> supplier) {
        Boolean hasKey = exists(key);
        if (Boolean.TRUE.equals(hasKey)) {
            return supplier.get();
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
        return supplier.get();
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
