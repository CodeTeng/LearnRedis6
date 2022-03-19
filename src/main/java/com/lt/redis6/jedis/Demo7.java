package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: Jedis-API-zset
 * @author: Lt
 * @date: 2022/3/19 21:51
 */
public class Demo7 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.zadd("zset01", 100d, "z3");
        jedis.zadd("zset01", 90d, "l4");
        jedis.zadd("zset01", 80d, "w5");
        jedis.zadd("zset01", 70d, "z6");
        List<String> list = jedis.zrange("zset01", 0, -1);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
