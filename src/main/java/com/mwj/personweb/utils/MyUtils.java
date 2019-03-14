package com.mwj.personweb.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {

  private static final Logger logger = LoggerFactory.getLogger(MyUtils.class);

  private static DataSource dataSource;

  private static ReentrantLock lock = new ReentrantLock();

  /** markdown解析器 */
  private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  private static final Pattern SLUG_REGEX =
      Pattern.compile("^[A-Za-z0-9_-]{5,100}$", Pattern.CASE_INSENSITIVE);

  /**
   * mds加密
   *
   * @param source
   * @return
   */
  public static String MD5encode(String source) {
    if (StringUtils.isBlank(source)) {
      return null;
    }
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
    }
    byte[] encode = messageDigest.digest(source.getBytes());
    StringBuffer hexString = new StringBuffer();
    for (byte anEncode : encode) {
      String hex = Integer.toHexString(0xff & anEncode);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  private static Cookie cookieRaw(String name, HttpServletRequest request) {
    Cookie[] servletCookies = request.getCookies();
    if (servletCookies == null) {
      return null;
    }
    for (Cookie c : servletCookies) {
      if (c.getName().equals(name)) {
        return c;
      }
    }
    return null;
  }

  public static String getUploadFilePath() {
    String path = MyUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    path = path.substring(1, path.length());
    try {
      path = URLDecoder.decode(path, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    int lastIndex = path.lastIndexOf("/") + 1;
    path = path.substring(0, lastIndex);
    File file = new File("");
    return file.getAbsolutePath() + "/";
  }

  /**
   * 获取随机数
   *
   * @param size
   * @return
   */
  public static String getRandomNumber(int size) {
    String num = "";
    for (int i = 0; i < size; i++) {
      double a = Math.random() * 9.0D;
      a = Math.ceil(a);
      int randomNum = (new Double(a)).intValue();
      num = num + randomNum;
    }
    return num;
  }

  /**
   * 提取html中的文字
   *
   * @param html
   * @return
   */
  public static String htmlToText(String html) {
    if (StringUtils.isNotBlank(html)) {
      return html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
    }
    return "";
  }

  public static boolean isPath(String slug) {
    if (StringUtils.isNotBlank(slug)) {
      if (slug.contains("/") || slug.contains(" ") || slug.contains(".")) {
        return false;
      }
      Matcher matcher = SLUG_REGEX.matcher(slug);
      return matcher.find();
    }
    return false;
  }

  /**
   * 判断是否是邮箱
   *
   * @param emailStr
   * @return
   */
  public static boolean isEmail(String emailStr) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    return matcher.find();
  }

  /**
   * 替换HTML脚本
   *
   * @param value
   * @return
   */
  public static String cleanXSS(String value) {
    // You'll need to remove the spaces from the html entities below
    value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
    value = value.replaceAll("'", "&#39;");
    value = value.replaceAll("eval\\((.*)\\)", "");
    value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
    value = value.replaceAll("script", "");
    return value;
  }

  /**
   * 判断文件类型是否是图片
   *
   * @param imageFile
   * @return
   */
  public static boolean isImage(InputStream imageFile) {
    try {
      Image img = ImageIO.read(imageFile);
      if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // 自动生成名字（中文）
  public static String getRandomJianHan(int len) {
    String ret = "";
    for (int i = 0; i < len; i++) {
      String str = null;
      int hightPos, lowPos; // 定义高低位
      Random random = new Random();
      hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
      lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
      byte[] b = new byte[2];
      b[0] = (new Integer(hightPos).byteValue());
      b[1] = (new Integer(lowPos).byteValue());
      try {
        str = new String(b, "GBK"); // 转成中文
      } catch (UnsupportedEncodingException ex) {
        ex.printStackTrace();
      }
      ret += str;
    }
    return ret;
  }

  // 生成随机用户名，数字和字母组成,
  public String getStringRandom(int length) {

    String val = "";
    Random random = new Random();

    // 参数length，表示生成几位随机数
    for (int i = 0; i < length; i++) {

      String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
      // 输出字母还是数字
      if ("char".equalsIgnoreCase(charOrNum)) {
        // 输出是大写字母还是小写字母
        int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
        val += (char) (random.nextInt(26) + temp);
      } else if ("num".equalsIgnoreCase(charOrNum)) {
        val += String.valueOf(random.nextInt(10));
      }
    }
    return val;
  }
}
