package org.hc.redis.hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/6
 */
@Component
public class HashRW {
    private final RedisTemplate<String, String> defaultRedisTemplate;

    @Autowired
    public HashRW(RedisTemplate<String, String> defaultRedisTemplate) {
        this.defaultRedisTemplate = defaultRedisTemplate;
    }

    public String hGet(String key, String field) {
        Object val = defaultRedisTemplate.opsForHash().get(key, field);
        return val == null ? null : val.toString();
    }

    public void hSet(String key, String field, String value) {
        defaultRedisTemplate.opsForHash().put(key, field, value);
    }

    public void hDel(String key, String field) {
        defaultRedisTemplate.opsForHash().delete(key, field);
    }

    public Boolean hasKey(String key, String field) {
        return defaultRedisTemplate.opsForHash().hasKey(key, field);
    }

    public Long hSize(String key) {
        return defaultRedisTemplate.opsForHash().size(key);
    }
}
