package org.hc.redis.zset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/6
 */
@Component
public class ZSetRW {
    private final RedisTemplate<String, String> defaultRedisTemplate;

    @Autowired
    public ZSetRW(RedisTemplate<String, String> defaultRedisTemplate) {
        this.defaultRedisTemplate = defaultRedisTemplate;
    }

    public Boolean add(String key, String member, double score) {
        return defaultRedisTemplate.opsForZSet().add(key, member, score);
    }

    public Double score(String key, String member) {
        return defaultRedisTemplate.opsForZSet().score(key, member);
    }

    public Long rank(String key, String member) {
        return defaultRedisTemplate.opsForZSet().rank(key, member);
    }

    public Double incrScore(String key, String member, double delta) {
        return defaultRedisTemplate.opsForZSet().incrementScore(key, member, delta);
    }

    public Set<String> range(String key, long start, long end) {
        return defaultRedisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<ZSetOperations.TypedTuple<String>> rangeWithScores(String key, long start, long end) {
        return defaultRedisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public Set<String> reverseRange(String key, long start, long end) {
        return defaultRedisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(String key, long start, long end) {
        return defaultRedisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    public Set<String> rangeByScore(String key, double min, double max) {
        return defaultRedisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Long remove(String key, String... members) {
        return defaultRedisTemplate.opsForZSet().remove(key, members);
    }

    public Long size(String key) {
        return defaultRedisTemplate.opsForZSet().size(key);
    }
}
