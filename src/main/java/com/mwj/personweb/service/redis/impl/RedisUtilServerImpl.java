package com.mwj.personweb.service.redis.impl;

import com.mwj.personweb.controller.HomeController;
import com.mwj.personweb.service.redis.RedisServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/** @Author: 母哥 @Date: 2019-02-14 18:02 @Version 1.0 */
@Service("redisService")
public class RedisUtilServerImpl implements RedisServer {

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Resource private RedisTemplate<String, ?> redisTemplate;

  @Override
  public boolean set(final String key, final String value) {
    boolean result =
        redisTemplate.execute(
            new RedisCallback<Boolean>() {
              @Override
              public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
              }
            });
    return result;
  }

  @Override
  public String get(final String key) {
    String result =
        redisTemplate.execute(
            new RedisCallback<String>() {
              @Override
              public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
              }
            });
    return result;
  }

  @Override
  public boolean expire(final String key, long expire) {
    return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
  }

  @Override
  public boolean remove(final String key) {
    boolean result =
        redisTemplate.execute(
            new RedisCallback<Boolean>() {
              @Override
              public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.del(key.getBytes());
                return true;
              }
            });
    return result;
  }

    @Override
    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error(key + "在当前redis不存在", e);
            return false;
        }
    }
}
