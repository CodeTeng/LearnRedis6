package com.lt.redis6.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Lt
 * @date: 2022/3/19 22:52
 */
@RestController
@RequestMapping("/redisTest")
public class RedisTestController {

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping
    public String testRedis() {
        // 设置值到redis
        redisTemplate.opsForValue().set("name", "kxt");
        // 从redis中获取
        String name = (String) redisTemplate.opsForValue().get("name");
        if (StringUtils.hasText(name)) {
            return name;
        }
        return null;
    }

    @GetMapping("/testLock")
    public void testLock() {
        // 1.从redis中获取锁，setnx 并设置过期时间
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 20, TimeUnit.SECONDS);
        if (lock) {
            // 2.1查询redis中的num值
            String value = (String) redisTemplate.opsForValue().get("num");
            // 2.2没有的话直接返回
            if (value == null) {
                return;
            }
            // 2.3有值就转换为int
            int num = Integer.parseInt(value);
            // 2.4把redis中的num+1
            redisTemplate.opsForValue().set("num", ++num);
            String lockUUID = (String) redisTemplate.opsForValue().get("lock");
            if (uuid.equals(lockUUID)) {
                // 2.5释放锁
                redisTemplate.delete("lock");
            }
        } else {
            // 获取锁失败
            try {
                Thread.sleep(100);
                testLock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
