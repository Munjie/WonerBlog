package com.mwj.personweb.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {

  /**
   * 格式化日期 使用线程安全的DateTimeFormatter
   *
   * @return “年-月-日”字符串
   */
  public static String getFormatDateForThree() {

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return now.format(format);
  }

  /**
   * 格式化日期 使用线程安全的DateTimeFormatter
   *
   * @return “年-月-日 时:分:秒”字符串
   */
  public static String getFormatDateForSix() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return now.format(format);
  }

  /**
   * 格式化日期 使用线程安全的DateTimeFormatter
   *
   * @return “年-月-日 时:分”字符串
   */
  public static String getFormatDateForFive() {

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return now.format(format);
  }

  /**
   * 解析日期
   *
   * @return
   */
  public static LocalDate getParseDateForThree(String date) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, format);
  }

  /** 解析日期 */
  public static LocalDate getParseDateForSix(String date) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return LocalDate.parse(date, format);
  }

  /**
   * 获得当前时间的时间戳
   *
   * @return 时间戳
   */
  public static long getLongTime() {
    Date now = new Date();
    return now.getTime() / 1000 + (long) ((Math.random() * 9 + 1) * 1000);
  }

  /** 时间中横杆转换为年 */
  public static String timeWhippletreeToYear(String str) {
    StringBuilder s = new StringBuilder();
    if (StringUtils.isNotBlank(str)) {

      s.append(str.substring(0, 4));
      s.append("年");
      s.append(str.substring(5, 7));
      s.append("月");
    }
    return String.valueOf(s);
  }

  /** 时间中的年转换为横杠 */
  public static String timeYearToWhippletree(String str) {
    StringBuilder s = new StringBuilder();
    if (StringUtils.isNotBlank(str)) {

      s.append(str.substring(0, 4));
      s.append("-");
      s.append(str.substring(5, 7));
    }
    return String.valueOf(s);
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
  /**
   * @description //java 时间戳 转 昨天前天 几小时前 刚刚
   * @param:
   * @return:
   * @date: 2019/3/13 10:40
   */
  public static String getTimeStateNew(String long_time) {
    String long_by_13 = "1000000000000";
    String long_by_10 = "1000000000";
    if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
      if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
        long_time = long_time + "000";
      }
    }
    Timestamp time = new Timestamp(Long.valueOf(long_time));
    Timestamp now = new Timestamp(System.currentTimeMillis());
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //    System.out.println("传递过来的时间:"+format.format(time));
    //    System.out.println("现在的时间:"+format.format(now));
    long day_conver = 1000 * 60 * 60 * 24;
    long hour_conver = 1000 * 60 * 60;
    long min_conver = 1000 * 60;
    long time_conver = now.getTime() - time.getTime();
    long temp_conver;
    //    System.out.println("天数:"+time_conver/day_conver);
    if ((time_conver / day_conver) < 3) {
      temp_conver = time_conver / day_conver;
      if (temp_conver <= 2 && temp_conver >= 1) {
        return temp_conver + "天前";
      } else {
        temp_conver = (time_conver / hour_conver);
        if (temp_conver >= 1) {
          return temp_conver + "小时前";
        } else {
          temp_conver = (time_conver / min_conver);
          if (temp_conver >= 1) {
            return temp_conver + "分钟前";
          } else {
            return "刚刚";
          }
        }
      }
    } else {
      return format.format(time);
    }
  }
}
