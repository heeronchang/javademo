package org.hc.redis.set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/6
 */
@Component
public class SetRW {
    private final RedisTemplate<String, String> defaultRedisTemplate;

    @Autowired
    public SetRW(RedisTemplate<String, String> defaultRedisTemplate) {
        this.defaultRedisTemplate = defaultRedisTemplate;
    }

    public void add(String key, String... values) {
        defaultRedisTemplate.opsForSet().add(key, values);
    }

    public void remove(String key, String... values) {
        defaultRedisTemplate.opsForSet().remove(key, values);
    }

    public Boolean exists(String key, String value) {
        return defaultRedisTemplate.opsForSet().isMember(key, value);
    }

    public Set<String> values(String key) {
        return defaultRedisTemplate.opsForSet().members(key);
    }

    public Set<String> union(String key, String otherKey) {
        return defaultRedisTemplate.opsForSet().union(key, otherKey);
    }

    public Set<String> innersect(String key, String otherKey) {
        return defaultRedisTemplate.opsForSet().intersect(key, otherKey);
    }

    public Set<String> diff(String key, String otherKey) {
        return defaultRedisTemplate.opsForSet().difference(key, otherKey);
    }
}
