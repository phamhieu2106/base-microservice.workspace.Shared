package com.henry;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Setter
public class RedisUtils {

    private static StringRedisTemplate redisTemplate;

    public static Boolean isExitKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
