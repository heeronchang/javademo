package org.hc.redis;

import org.hc.redis.list.ListRW;
import org.hc.redis.string.StringRW;
import org.hc.redis.zset.ZSetRW;
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

    @Autowired
    private ListRW listRW;

    @Autowired
    private ZSetRW zSetRW;

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

    @Test
    public void testListRWPush() {
        listRW.lPush("list-key", "1");
        listRW.lPush("list-key", "2");
        listRW.lPush("list-key", "3");
        listRW.lPush("list-key", "2");
        listRW.rPush("list-key", "4");
    }

    @Test
    public void testListRWRange() {
        System.out.println(listRW.range("list-key", 0, -1));
    }

    @Test
    public void testListRWGet() {
        System.out.println(listRW.get("list-key", 2));
    }

    @Test
    public void testListRWSet() {
        listRW.set("list-key", 2, "22");
    }

    @Test
    public void testListRWRemove() {
        listRW.remove("list-key", 2, "22");
    }

    @Test
    public void testListSize() {
        System.out.println(listRW.size("list-key"));
    }

    @Test
    public void testListRWTrim() {
        listRW.trim("list-key", 0, 1);
    }

    @Test
    public void testZSetRW() {
        System.out.println(zSetRW.add("zset-key", "zset-member1", 1));
        System.out.println(zSetRW.add("zset-key", "zset-member1", 2));
        zSetRW.add("zset-key", "zset-member2", 1);
        zSetRW.add("zset-key", "zset-member3", 3);
    }

    @Test
    public void testZSetRWRemove() {
        zSetRW.remove("zset-key", "zset-member1", "zset-member2");
    }

    @Test
    public void testZSetRWRange() {
        System.out.println(zSetRW.rank("zset-key", "zset-member2"));
        System.out.println(zSetRW.range("zset-key", 0, -1));
        System.out.println(zSetRW.rangeWithScores("zset-key", 0, -1));
        System.out.println(zSetRW.reverseRange("zset-key", 0, -1));
        System.out.println(zSetRW.reverseRangeWithScores("zset-key", 0, -1));
    }
}
