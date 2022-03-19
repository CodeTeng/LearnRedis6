package com.lt.redis6.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
