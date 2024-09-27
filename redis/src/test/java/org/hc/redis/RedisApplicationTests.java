package org.hc.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisApplicationTests {

    // 默认redisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    // 自定义默认redisTemplate
    @Autowired
    private RedisTemplate<String, String> defaultRedisTemplate;

    // 自定义从redisTemplate
    @Autowired
    private RedisTemplate<String, String> slaveRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testDefaultConfig() {
        redisTemplate.opsForValue().set("nick1", "zhangsan");
        System.out.println(redisTemplate.opsForValue().get("nick1"));
    }

    @Test
    public void testDIYDefaultConfig() {
        defaultRedisTemplate.opsForValue().set("name", "lisi");
        System.out.println(defaultRedisTemplate.opsForValue().get("name"));
    }

    @Test
    public void testDIYSlaveConfig() {
        slaveRedisTemplate.opsForValue().set("name2", "wangwu");
        System.out.println(slaveRedisTemplate.opsForValue().get("name2"));
    }

}
