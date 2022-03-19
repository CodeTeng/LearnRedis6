package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: Jedis-API-Hash
 * @author: Lt
 * @date: 2022/3/19 21:51
 */
public class Demo6 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.hset("hash1","userName","lisi");
        System.out.println(jedis.hget("hash1", "userName"));
        Map<String, String> map = new HashMap<>();
        map.put("telphone","13810169999");
        map.put("address","atguigu");
        map.put("email","abc@163.com");
        jedis.hmset("hash2", map);
        List<String> list = jedis.hmget("hash2", "telphone", "address");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
