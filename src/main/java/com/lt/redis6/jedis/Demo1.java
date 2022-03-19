package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

/**
 * @description: jedis入门
 * @author: Lt
 * @date: 2022/3/19 21:39
 */
public class Demo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String ping = jedis.ping();
        System.out.println("连接成功：" + ping);
        jedis.close();
    }
}
