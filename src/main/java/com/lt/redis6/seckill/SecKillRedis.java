package com.lt.redis6.seckill;

import com.lt.redis6.utils.JedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @description: 秒杀案例
 * @author: Lt
 * @date: 2022/3/20 15:19
 */
public class SecKillRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());
        jedis.close();
    }

    //秒杀过程
    public static boolean doSecKill(String uid, String prodid) {
        // 1 uid和prodid非空判断
        if (uid == null || prodid == null) {
            return false;
        }
        // 2.利用redis连接池连接redis
        Jedis jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
        //3 拼接key
        // 3.1 库存key
        String kcKey = "sk:" + prodid + ":qt";
        // 3.2 秒杀成功用户key
        String userKey = "sk:" + prodid + ":user";
        // 监视库存---利用乐观锁
        jedis.watch(kcKey);
        // 4.获取库存，如果库存null，秒杀还没有开始
        String kc = jedis.get(kcKey);
        if (kc == null) {
            System.out.println("秒杀还未开始，请等待！！！");
            jedis.close();
            return false;
        }
        // 5 判断用户是否重复秒杀操作
        if (jedis.sismember(userKey, uid)) {
            System.out.println("你已经秒杀过了，不能再次秒杀！！！");
            jedis.close();
            return false;
        }
        // 6.判断如果商品数量，库存数量小于1，秒杀结束
        if (Integer.parseInt(kc) < 1) {
            System.out.println("库存为空，秒杀结束！！！");
            jedis.close();
            return false;
        }
        // 7.秒杀过程
        Transaction transaction = jedis.multi();
        // 组队操作
        // 7.1 库存-1
        transaction.decr(kcKey);
        // 7.2 把秒杀成功用户添加清单里面
        transaction.sadd(userKey, uid);
        // 提交
        List<Object> list = transaction.exec();
        if (list == null || list.size() == 0) {
            System.out.println("秒杀失败了....");
            jedis.close();
            return false;
        }
        System.out.println("秒杀成功了");
        jedis.close();
        return true;
    }
}
