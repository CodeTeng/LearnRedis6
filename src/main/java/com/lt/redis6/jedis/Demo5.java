package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @description: Jedis-API-Set
 * @author: Lt
 * @date: 2022/3/19 21:51
 */
public class Demo5 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.sadd("orders", "order01");
        jedis.sadd("orders", "order02");
        jedis.sadd("orders", "order03");
        jedis.sadd("orders", "order04");
        Set<String> orders = jedis.smembers("orders");
        for (String order : orders) {
            System.out.println(order);
        }
        System.out.println("=========================");
        jedis.srem("orders", "order02");
        orders = jedis.smembers("orders");
        for (String order : orders) {
            System.out.println(order);
        }
    }
}
