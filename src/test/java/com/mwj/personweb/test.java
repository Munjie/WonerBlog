package com.mwj.personweb;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** @Author: 母哥 @Date: 2019-03-07 15:49 @Version 1.0 */
public class test {

  public static void main(String[] args) {
    /* // TODO Auto-generated method stub
    Random rand = new Random();
    StringBuilder sb = new StringBuilder();
    String strAll = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (int i = 0; i < 6; i++) {
        int f = (int) (Math.random() * 62);
        sb.append(strAll.charAt(f));
    }
    System.out.println(sb);*/

    /* String dataStr = "2014-09-01 19:47";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    try {
      Date ckDate = sdf.parse(dataStr);
      System.out.println("原始时间为：" + dataStr);
      System.out.println("转换后时间为：" + getTime(ckDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }*/
    System.out.println(test.formateTime(System.currentTimeMillis()));
    System.out.println(test.getTimeStateNew(String.valueOf(1000000000000l)));
  }

  // 将传入时间与当前时间进行对比，是否今天\昨天\前天\同一年
  private static String getTime(Date date) {
    boolean sameYear = false;
    String todySDF = "HH:mm";
    String yesterDaySDF = "昨天";
    String beforYesterDaySDF = "前天";
    String otherSDF = "MM-dd";
    String otherYearSDF = "yyyy-MM-dd";
    SimpleDateFormat sfd = null;
    String time = "";
    Calendar dateCalendar = Calendar.getInstance();
    dateCalendar.setTime(date);
    Date now = new Date();
    Calendar todayCalendar = Calendar.getInstance();
    todayCalendar.setTime(now);
    todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
    todayCalendar.set(Calendar.MINUTE, 0);

    if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
      sameYear = true;
    } else {
      sameYear = false;
    }

    if (dateCalendar.after(todayCalendar)) { // 判断是不是今天
      sfd = new SimpleDateFormat(todySDF);
      time = sfd.format(date);
      return time;
    } else {
      todayCalendar.add(Calendar.DATE, -1);
      if (dateCalendar.after(todayCalendar)) { // 判断是不是昨天
        // sfd = new SimpleDateFormat(yesterDaySDF);
        // time = sfd.format(date);
        time = yesterDaySDF;
        return time;
      }
      todayCalendar.add(Calendar.DATE, -2);
      if (dateCalendar.after(todayCalendar)) { // 判断是不是前天
        // sfd = new SimpleDateFormat(beforYesterDaySDF);
        // time = sfd.format(date);
        time = beforYesterDaySDF;
        return time;
      }
    }

    if (sameYear) {
      sfd = new SimpleDateFormat(otherSDF);
      time = sfd.format(date);
    } else {
      sfd = new SimpleDateFormat(otherYearSDF);
      time = sfd.format(date);
    }

    return time;
  }

  private static String formateTime(Long time) {
    Date dateparam = new Date(time);

    Date currentTime = new Date(/*System.currentTimeMillis()*/ 1000000000000l);

    switch (currentTime.getDate() - dateparam.getDate()) {
      case 0:
        int i = currentTime.getHours() - dateparam.getHours();
        if (i > 0) {
          int i2 = currentTime.getMinutes() - dateparam.getMinutes();
          if (i2 > 0) return i + "小时前";
          else if (i2 > -60) return 60 + i2 + "分钟前";
          else return "刚刚";
        } else {
          int i1 = currentTime.getMinutes() - dateparam.getMinutes();
          if (i1 > 0) {
            return i1 + "分钟前";
          } else return "刚刚";
        }
      case 1:
        return "昨天"
            + formateStr(dateparam.getHours() + "")
            + ":"
            + formateStr(dateparam.getMinutes() + "")
            + ":"
            + formateStr("" + dateparam.getSeconds());
      case 2:
        return "前天"
            + formateStr(dateparam.getHours() + "")
            + ":"
            + formateStr(dateparam.getMinutes() + "")
            + ":"
            + formateStr("" + dateparam.getSeconds());
      default:
        return currentTime.getDate() - dateparam.getDate() + "天前";
    }
  }

  private static String formateStr(String str) {
    return new String().format("%02d", str);
  }

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

  public static String format(long timeStamp) {
    long curTimeMillis = System.currentTimeMillis();
    Date curDate = new Date(curTimeMillis);
    int todayHoursSeconds = curDate.getHours() * 60 * 60;
    int todayMinutesSeconds = curDate.getMinutes() * 60;
    int todaySeconds = curDate.getSeconds();
    int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
    long todayStartMillis = curTimeMillis - todayMillis;
    if (timeStamp >= todayStartMillis) {
      return "今天";
    }
    int oneDayMillis = 24 * 60 * 60 * 1000;
    long yesterdayStartMilis = todayStartMillis - oneDayMillis;
    if (timeStamp >= yesterdayStartMilis) {
      return "昨天";
    }
    long yesterdayBeforeStartMilis = yesterdayStartMilis - oneDayMillis;
    if (timeStamp >= yesterdayBeforeStartMilis) {
      return "前天";
    }
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(new Date(timeStamp));
  }
}
