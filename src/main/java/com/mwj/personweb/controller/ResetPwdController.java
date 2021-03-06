package com.mwj.personweb.controller;

import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.EmailUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/** @Author: 母哥 @Date: 2019-03-07 17:41 @Version 1.0 */
@Controller
public class ResetPwdController {

  private static Logger logger = LoggerFactory.getLogger(ResetPwdController.class);

  @Autowired private SessionRegistry sessionRegistry;

  @Autowired private ISysUserService userService;

  @Autowired protected AuthenticationManager authenticationManager;

  @Autowired private EmailUtils emailUtils;

  @Autowired private RedisServer redisServer;

  @PostMapping(value = "/checkEmail")
  @ResponseBody
  public JSONObject checkEmail(String email, HttpServletRequest request) {

    return userService.findUserByEmail(email);
  }

  @PostMapping(value = "/sendEmailCode")
  @ResponseBody
  public JSONObject sendEmailCode(String email, HttpServletRequest request) {

    JSONObject jsonObject = new JSONObject();
    JSONObject userByEmail = userService.findUserByEmail(email);
    if (userByEmail.get("status").equals("200")) {
      try {
        if (StringUtils.isNotBlank(email)) {
          int code = (int) ((Math.random() * 9 + 1) * 100000);
          redisServer.set(String.valueOf(code), String.valueOf(code));
          emailUtils.sendRestPwdCode(email, String.valueOf(code));
        } else {
          jsonObject.put("status", "400");
        }
      } catch (Exception e) {
        logger.error("用户" + email + "重置密码异常", e);
        jsonObject.put("status", "400");
        jsonObject.put("msg", "发送验证码失败");
      }
    }
    return jsonObject;
  }

  @PostMapping(value = "/checkEmailCode")
  @ResponseBody
  public JSONObject checkEmailCode(String emailCode, HttpServletRequest request) {

    JSONObject jsonObject = new JSONObject();
    if (StringUtils.isNotBlank(emailCode)) {
      String redisEmailCode = redisServer.get(emailCode);
      if (StringUtils.isNotBlank(redisEmailCode) && redisEmailCode.equals(emailCode)) {

        jsonObject.put("status", "200");
        jsonObject.put("msg", "验证码OK");
      } else {

        jsonObject.put("status", "400");
        jsonObject.put("msg", "验证码不匹配");
      }
    } else {
      jsonObject.put("status", "400");
      jsonObject.put("msg", "输入验证码有误");
    }
    return jsonObject;
  }

  @PostMapping(value = "/resetPwd")
  @ResponseBody
  public JSONObject resetPwd(SysUser sysUser, HttpServletRequest request) {

    JSONObject jsonObject = userService.resetPassword(sysUser);
    if (jsonObject.get("status").equals("200")) {

      emailUtils.pwdResetSucSender(sysUser.getEmail(), sysUser.getPassword(), "www.munjie.com");
    }

    return jsonObject;
  }
}
