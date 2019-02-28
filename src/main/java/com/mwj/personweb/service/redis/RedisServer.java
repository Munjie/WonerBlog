package com.mwj.personweb.service.redis;

/** @Author: 母哥 @Date: 2019-02-14 18:02 @Version 1.0 */
public interface RedisServer {

  /**
   * set存数据
   *
   * @param key
   * @param value
   * @return
   */
  boolean set(String key, String value);

  /**
   * get获取数据
   *
   * @param key
   * @return
   */
  String get(String key);

  /**
   * 设置有效天数
   *
   * @param key
   * @param expire
   * @return
   */
  boolean expire(String key, long expire);

  /**
   * 移除数据
   *
   * @param key
   * @return
   */
  boolean remove(String key);
}
