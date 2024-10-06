package org.hc.redis.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * list使用场景一般为队列或者堆栈
 * 常见添加元素有从左边添加和从右边添加
 *
 * @author 叽哒嘎叽
 * @since 2024/10/6
 */
@Component
public class ListRW {
    private final RedisTemplate<String, String> defaultRedisTemplate;

    @Autowired
    public ListRW(RedisTemplate<String, String> defaultRedisTemplate) {
        this.defaultRedisTemplate = defaultRedisTemplate;
    }

    public void lPush(String key, String value) {
        defaultRedisTemplate.opsForList().leftPush(key, value);
    }

    public void rPush(String key, String value) {
        defaultRedisTemplate.opsForList().rightPush(key, value);
    }

    public String get(String key, long index) {
        return defaultRedisTemplate.opsForList().index(key, index);
    }

    public void set(String key, long index, String value) {
        defaultRedisTemplate.opsForList().set(key, index, value);
    }

    public List<String> range(String key, long start, long end) {
        return defaultRedisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 删除列表中值为value的元素，总共删除count次；
     * <p>
     * 如原来列表为 【1， 2， 3， 4， 5， 2， 1， 2， 5】
     * 传入参数 value=2, count=1 表示删除一个列表中value为2的元素
     * 则执行后，列表为 【1， 3， 4， 5， 2， 1， 2， 5】
     *
     * @param key
     * @param value
     * @param count
     */
    public void remove(String key, long count, String value) {
        defaultRedisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 删除list首尾，只保留 [start, end] 之间的值
     *
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, long start, long end) {
        defaultRedisTemplate.opsForList().trim(key, start, end);
    }

    public Long size(String key) {
        return defaultRedisTemplate.opsForList().size(key);
    }
}
