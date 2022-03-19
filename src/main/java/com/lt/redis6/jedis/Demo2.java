package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @description: Jedis-API-Key
 * @author: Lt
 * @date: 2022/3/19 21:41
 */
public class Demo2 {
    public static void main(String[] args) {
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
