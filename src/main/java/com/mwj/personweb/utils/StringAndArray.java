package com.mwj.personweb.utils;

/** @Author: 母哥 @Date: 2019-02-15 12:06 @Version 1.0 */
public class StringAndArray {

  /**
   * 字符串转换成字符串数组
   *
   * @param str 字符串
   * @return 转换后的字符串数组
   */
  public static String[] stringToArray(String str) {
    String[] array = str.split(",");
    return array;
  }

  /**
   * 字符串数组拼接成字符串
   *
   * @param articleTags 字符串数组
   * @return 拼接后的字符串
   */
  public static String arrayToString(String[] articleTags) {
    String buffered = "";
    for (String s : articleTags) {
      if ("".equals(buffered)) {
        buffered += s;
      } else {
        buffered += "," + s;
      }
    }
    return buffered;
  }
}
