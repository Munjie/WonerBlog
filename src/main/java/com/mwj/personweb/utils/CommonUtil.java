package com.mwj.personweb.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: 母哥 @Date: 2019-03-08 14:22 @Version 1.0
 */
public class CommonUtil {

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

    public static String gravatarImg(String email) {
        String avatarUrl = "https://secure.gravatar.com/avatar";
        if (StringUtils.isBlank(email)) {
            return avatarUrl;
        }
        String hash = MD5encode(email.trim().toLowerCase());
        return avatarUrl + "/" + hash;
    }

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

    /**
     * unicode编码转中文
     *
     * @param dataStr unicode编码
     * @return 中文
     */
    public static String unicodeToString(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            // 16进制parse整形字符串。
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    /**
     * 中文转unicode编码
     *
     * @param gbString 汉字
     * @return unicode编码
     */
    public static String stringToUnicode(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
  }
}
