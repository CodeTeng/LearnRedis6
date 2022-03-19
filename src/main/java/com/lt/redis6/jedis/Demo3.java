package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

/**
 * @description: Jedis-Api-String
 * @author: Lt
 * @date: 2022/3/19 21:49
 */
public class Demo3 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");
        System.out.println(jedis.mget("str1", "str2", "str3"));
    }
}
