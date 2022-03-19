package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @description: Jedis-API-List
 * @author: Lt
 * @date: 2022/3/19 21:51
 */
public class Demo4 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        List<String> list = jedis.lrange("mylist", 0, -1);
        if (list.size() == 0) {
            System.out.println("为空");
        } else {
            for (String element : list) {
                System.out.println(element);
            }
        }
    }
}
