package com.lt.redis6.jedis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description:
 * @author: Lt
 * @date: 2022/3/19 21:45
 */
@SpringBootTest
public class Demo1Test {

    @Test
    public void testInit() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String ping = jedis.ping();
        System.out.println("连接成功：" + ping);
        jedis.close();
    }

    @Test
    public void testApiKey() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");
        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.exists("k2"));
        System.out.println(jedis.ttl("k1"));
    }
}