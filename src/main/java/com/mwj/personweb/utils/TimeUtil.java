package com.mwj.personweb.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/** @Author: 母哥 @Date: 2019-02-21 17:17 @Version 1.0 */
public class TimeUtil {

  private static final Logger log = LoggerFactory.getLogger(TimeUtil.class);

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

  public static long calcLoginTime(Date loginTime, Date outTime) {

    try {

      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      /* Date parse = format.parse(format.format(loginTime));
      Date date = format.parse(format.format(outTime));*/

      return dateDiff(format.format(loginTime), format.format(outTime), "yyyy-MM-dd HH:mm:ss", "m");

      /*
      long between = date.getTime() - parse.getTime();
      long day = between / (24 * 60 * 60 * 1000);
      long hour = (between / (60 * 60 * 1000) - day * 24);
      long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
      long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
      return min;*/
    } catch (Exception e) {
      log.error("计算登陆时长异常", e);
    }
    return 0;
  }

  public static Long dateDiff(String startTime, String endTime, String format, String str) {
    // 按照传入的格式生成一个simpledateformate对象
    SimpleDateFormat sd = new SimpleDateFormat(format);
    long nd = 1000 * 24 * 60 * 60; // 一天的毫秒数
    long nh = 1000 * 60 * 60; // 一小时的毫秒数
    long nm = 1000 * 60; // 一分钟的毫秒数
    long ns = 1000; // 一秒钟的毫秒数
    long diff;
    long day = 0;
    long hour = 0;
    long min = 0;
    long sec = 0;
    // 获得两个时间的毫秒时间差异
    try {
      diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
      day = diff / nd; // 计算差多少天
      hour = diff % nd / nh + day * 24; // 计算差多少小时
      min = diff % nd % nh / nm + day * 24 * 60; // 计算差多少分钟
      sec = diff % nd % nh % nm / ns; // 计算差多少秒
      // 输出结果
      System.out.println(
          "时间相差："
              + day
              + "天"
              + (hour - day * 24)
              + "小时"
              + (min - day * 24 * 60)
              + "分钟"
              + sec
              + "秒。");
      System.out.println("hour=" + hour + ",min=" + min);
      if (str.equalsIgnoreCase("h")) {
        return hour;
      } else {
        return min;
      }

    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (str.equalsIgnoreCase("h")) {
      return hour;
    } else {
      return min;
    }
  }
}
