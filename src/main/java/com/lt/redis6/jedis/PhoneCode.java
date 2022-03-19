package com.lt.redis6.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @description: 校验验证码
 * @author: Lt
 * @date: 2022/3/19 22:06
 */
public class PhoneCode {
    public static void main(String[] args) {
        String code = getCode();
        String phone = "15290654216";
        verifyCode(phone);
        // 获取到后校验
//        getRedisCode(phone, "575686");
    }

    /**
     * 1.生成6位数字的验证码
     *
     * @return 验证码
     */
    public static String getCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(10);
            stringBuilder.append(index);
        }
        return stringBuilder.toString();
    }

    /**
     * 2.每个手机每天只能发送三次，验证码放到redis中，设置过期时间
     *
     * @param phone 手机号
     */
    public static void verifyCode(String phone) {
        // 1.连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 拼接key
        // 手机发送的key
        String countKey = "VerifyCode" + phone + ":count";
        // 验证码的key
        String codeKey = "VerifyCode" + phone + ":code";

        // 验证码放到redis中，设置过期时间
        String code = getCode();
        jedis.setex(codeKey, 120, code);

        // 每个手机每天只能发送三次
        String countPhone = jedis.get(countKey);
        if (countPhone == null) {
            // 第一次发送 设置发送数量
            jedis.setex(countKey, 24 * 60 * 60, "1");
            System.out.println("第一次发送验证码");
        } else if (Integer.parseInt(countPhone) <= 2) {
            // 发送次数+1
            jedis.incr(countKey);
            System.out.println("再次发送验证码");
        } else if (Integer.parseInt(countPhone) > 2) {
            // 不能再发送
            System.out.println("今天发送次数已经超过三次，不能再发送了");
            jedis.close();
        }
    }

    /**
     * 3.验证码校验
     *
     * @param code 验证码
     */
    public static void getRedisCode(String phone, String code) {
        // 1.连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 验证码的key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        if (redisCode.equals(code)) {
            System.out.println("校验成功");
        } else {
            System.out.println("校验失败");
        }
        jedis.close();
    }
}
