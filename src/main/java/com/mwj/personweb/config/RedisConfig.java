package com.mwj.personweb.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/** @Author: 母哥 @Date: 2019-02-13 17:26 @Version 1.0 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  private static final Logger loger = LoggerFactory.getLogger(RedisConfig.class);

  /** 自定义生成key的规则 */
  @Bean
  public KeyGenerator autoKeyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
          sb.append(obj.toString());
        }
        return sb.toString();
      }
    };
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
    return redisCacheManager;
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    //// 解决键、值序列化问题
    StringRedisTemplate template = new StringRedisTemplate(factory);
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }
}
