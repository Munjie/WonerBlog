package com.mwj.personweb.utils;

import javax.servlet.http.HttpServletRequest;

/** @Author: 母哥 @Date: 2019-02-28 16:09 @Version 1.0 */
public class CodeUtil {

  /**
   * 将获取到的前端参数转为string类型
   *
   * @param request
   * @param key
   * @return
   */
  public static String getString(HttpServletRequest request, String key) {
    try {
      String result = request.getParameter(key);
      if (result != null) {
        result = result.trim();
      }
      if ("".equals(result)) {
        result = null;
      }
      return result;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 验证码校验
   *
   * @param request
   * @return
   */
  public static boolean checkVerifyCode(HttpServletRequest request) {
    // 获取生成的验证码
    String verifyCodeExpected =
        (String)
            request
                .getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    // 获取用户输入的验证码
    String verifyCodeActual = CodeUtil.getString(request, "verifyCode");
    if (verifyCodeActual == null || !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
      return true;
    }
    return true;
  }
}
