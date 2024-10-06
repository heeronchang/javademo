package org.hc.redis.string;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * String 数据结构读写
 *
 * @author 叽哒嘎叽
 * @since 2024/9/28
 */
@Component
public class StringRW {
    private final RedisTemplate<String, String> defaultRedisTemplate;

    @Autowired
    public StringRW(RedisTemplate<String, String> defaultRedisTemplate) {
        this.defaultRedisTemplate = defaultRedisTemplate;
    }

    public void set(String key, String value) {
        defaultRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置key 带过期时间
     * @param key
     * @param value
     * @param timeout
     */
    public void setWithExpire(String key, String value, long timeout) {
        defaultRedisTemplate.opsForValue().set(key, value, timeout);
//        defaultRedisTemplate.expire(key, Duration.ofMillis(timeout));
    }

    /**
     * redis 锁的使用
     * @param key
     * @param value
     * @return
     */
    public Boolean setNx(String key, String value) {
        return defaultRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Object get(String key) {
        return defaultRedisTemplate.opsForValue().get(key);
    }

    public Object getAndSet(String key, String value) {
        return defaultRedisTemplate.opsForValue().getAndSet(key, value);
    }

    public Boolean delete(String key) {
        return defaultRedisTemplate.delete(key);
    }

    public void batchSet(Map<String, String> kvs) {
        defaultRedisTemplate.opsForValue().multiSet(kvs);
    }

    public List<String> batchGet(List<String> keys) {
        return defaultRedisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * execute 方式使用 set
     */

    public void executeSet(String key, String value) {
        defaultRedisTemplate.execute((RedisCallback<Void>) con -> {
            con.set(key.getBytes(), value.getBytes());
            return null;
        });
    }

    /**
     * execute 方式使用 get
     *
     * @param key
     * @return
     */
    public String executeGet(String key) {
        return defaultRedisTemplate.execute((RedisCallback<String>) con -> new String(Objects.requireNonNull(con.get(key.getBytes()))));
    }

    /**
     * 计数 增加
     * @param key
     * @param delta
     * @return
     */
    public Long increment(String key, long delta) {
        return defaultRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 计数 减少
     * @param key
     * @param delta
     * @return
     */
    public Long decrement(String key, long delta) {
        return defaultRedisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 统计网站的日活，用户首次登陆时，根据用户id，设置位图中下标为userId的值为1，表示这个用户激活了；
     * 然后一天结束之后，只需要统计这个位图中为1的个数就可以知道每日的日活；
     * 也可以借此来统计每个用户的活跃状况
     * @param key
     * @param index
     * @param tag
     * @return
     */
    public Boolean setBit(String key, Integer index, boolean tag) {
        return defaultRedisTemplate.opsForValue().setBit(key, index, tag);
    }

    public Boolean getBit(String key, Integer index) {
        return defaultRedisTemplate.opsForValue().getBit(key, index);
    }

    public Long bitCount(String key) {
        return defaultRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    public Long bitCount(String key, long start, long end) {
        return defaultRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return defaultRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }
}
