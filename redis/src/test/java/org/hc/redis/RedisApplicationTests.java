package org.hc.redis;

import org.hc.redis.string.StringRW;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.HashMap;

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

    @Autowired
    private StringRW stringRW;

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

    @Test
    public void testStringRW() {
        stringRW.set("string-key1", "string-value1");
        System.out.println(stringRW.get("string-key1"));

        stringRW.batchSet(new HashMap<String, String>() {{
            put("string-key2", "string-value2");
            put("string-key3", "string-value3");
        }});

        System.out.println(stringRW.batchGet(Arrays.asList(new String[]{"string-key2", "string-key3"})));

        System.out.println(stringRW.getAndSet("string-key1", "new-string-value1"));

//        stringRW.delete("string-key4");

        stringRW.executeSet("string-key5", "string-value5");
        System.out.println(stringRW.executeGet("string-key5"));
    }

    @Test
    public void testStringRWIncrDecr() {
        stringRW.increment("incr-decr-key1", 10);
        System.out.println(stringRW.get("incr-decr-key1"));
        stringRW.decrement("incr-decr-key1", 1);
        System.out.println(stringRW.get("incr-decr-key1"));
    }

    @Test
    public void testStringRWBit() {
//        stringRW.setBit("bit-key", 1, true);
//        stringRW.setBit("bit-key", 100, true);
//        System.out.println(stringRW.getBit("bit-key", 1));
//        System.out.println(stringRW.bitCount("bit-key"));
//        System.out.println(stringRW.bitCount("bit-key", 0, 100));

//        stringRW.setBit("bit-key2", 1, true);
//        stringRW.setBit("bit-key2", 2, true);
//        stringRW.setBit("bit-key2", 100, false);
        System.out.println(stringRW.getBit("bit-key2", 1));
        System.out.println(stringRW.getBit("bit-key2", 2));
        System.out.println(stringRW.getBit("bit-key2", 100));


        stringRW.bitOp(RedisStringCommands.BitOperation.AND, "bit-key-save", "bit-key", "bit-key2");
        System.out.println(stringRW.getBit("bit-key-save", 1));
        System.out.println(stringRW.getBit("bit-key-save", 2));
        System.out.println(stringRW.getBit("bit-key-save", 100));
    }

    @Test
    public void testStringRWNX() {
        System.out.println(stringRW.setNx("nx-key", "nx-value"));
        System.out.println(stringRW.setNx("nx-key", "nx-value"));
        System.out.println(stringRW.delete("nx-key"));
        System.out.println(stringRW.setNx("nx-key", "nx-value"));
    }

}
