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
}
