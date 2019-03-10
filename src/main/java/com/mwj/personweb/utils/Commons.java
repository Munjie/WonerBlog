package com.mwj.personweb.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author tangj
 * @date 2018/1/21 21:56
 */
@Component
public class Commons {

  /**
   * 返回gravatar头像地址
   *
   * @param email
   * @return
   */
  public static String gravatar(String email) {
    String avatarUrl = "https://secure.gravatar.com/avatar";
    if (StringUtils.isBlank(email)) {
      return avatarUrl;
    }
    String hash = MyUtils.MD5encode(email.trim().toLowerCase());
    return avatarUrl + "/" + hash;
  }

  /**
   * 格式化unix时间戳为日期
   *
   * @param unixTime
   * @return
   */
  public static String fmtdate(Integer unixTime) {
    return fmtdate(unixTime, "yyyy-MM-dd");
  }

  /**
   * 格式化unix时间戳为日期
   *
   * @param unixTime
   * @param patten
   * @return
   */
  public static String fmtdate(Integer unixTime, String patten) {
    if (null != unixTime && StringUtils.isNotBlank(patten)) {
      return DateKit.formatDateByUnixTime(unixTime, patten);
    }
    return "";
  }
}
