package com.mwj.personweb.controller;

import com.mwj.personweb.exception.MyRuntimeException;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.EmailUtils;
import com.mwj.personweb.utils.JsonUtil;
import com.mwj.personweb.utils.PageUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/** @Author: 母哥 @Date: 2019-02-28 11:08 @Version 1.0 */
@RequestMapping("/admin")
@Controller
public class BackController {

  private static Logger logger = LoggerFactory.getLogger(BackController.class);

  @Autowired private SessionRegistry sessionRegistry;

  @Autowired private ISysUserService userService;

  @Autowired protected AuthenticationManager authenticationManager;

  @Autowired private EmailUtils emailUtils;

  @Autowired private RedisServer redisServer;

  @Autowired private PageUtil pageUtil;

  @GetMapping(value = "/toLogin")
  public String backLogin() {
    return "back/login";
  }

  @RequestMapping("/user")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_USER')")
  public String printUser() {
    return "你有ROLE_USER角色";
  }

  @RequestMapping(value = "/login/success")
  public void loginSuccess(
      Authentication authentication, HttpServletResponse response, HttpSession session)
      throws Exception {
    SysUser user = null;
    if (authentication != null && authentication.getName() != null) {
      session.setAttribute("name", authentication.getName());
      user = userService.findByName(authentication.getName());
      redisServer.set(user.getName(), JsonUtil.getObjectToJson(user));
      logger.info("当前登陆用户为：：" + authentication.getName());
    }

    response.sendRedirect("/");
  }

  @RequestMapping("/login")
  public String login() {

    return "back/login";
  }

  @RequestMapping("/invalid")
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public void invalid(HttpServletResponse response) throws Exception {
    response.sendRedirect("/");
  }

  @RequestMapping("/login/error")
  public String loginError(
      Authentication authentication,
      HttpServletRequest request,
      HttpServletResponse response,
      Model model)
      throws Exception {
    response.setContentType("text/html;charset=utf-8");
    MyRuntimeException exception =
        (MyRuntimeException) request.getSession().getAttribute("SECURITY_EXCEPTION");
    //    try {
    //    //  response.getWriter().write(exception.toString());
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    model.addAttribute("loginError", true);
    model.addAttribute("errorMsg", exception.getMessage());
    return pageUtil.forward(authentication, model, "front/login");
  }

  @GetMapping("/kick")
  @ResponseBody
  public String removeUserSessionByUsername(@RequestParam String username) {
    int count = 0;

    // 获取session中所有的用户信息
    List<Object> users = sessionRegistry.getAllPrincipals();
    for (Object principal : users) {
      if (principal instanceof User) {
        String principalName = ((User) principal).getUsername();
        if (principalName.equals(username)) {
          // 参数二：是否包含过期的Session
          List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
          if (null != sessionsInfo && sessionsInfo.size() > 0) {
            for (SessionInformation sessionInformation : sessionsInfo) {
              sessionInformation.expireNow();
              count++;
            }
          }
        }
      }
    }
    return "操作成功，清理session共" + count + "个";
  }

  @RequestMapping("/403")
  public String error403() {
    return "error/403";
  }

  @PostMapping(value = "/checkName")
  @ResponseBody
  public JSONObject checkName(String name, HttpServletRequest request) {

    JSONObject object = new JSONObject();

    if (userService.isExitUser(name)) {
      object.put("status", "400");

    } else {
      object.put("status", "200");
    }
    return object;
  }

  @PostMapping(value = "/register")
  @ResponseBody
  public JSONObject register(SysUser sysUser, HttpServletRequest request) {

    JSONObject jsonObject = new JSONObject();
    if (StringUtils.isBlank(sysUser.getName())) {
      jsonObject.put("status", "400");
      jsonObject.put("msg", "用户名不能为空");
      return jsonObject;
    } else if (userService.isExitUser(sysUser.getName())) {
      jsonObject.put("status", "400");
      jsonObject.put("msg", "用户名已存在");
      return jsonObject;
    } else if (StringUtils.isBlank(sysUser.getEmail())) {
      jsonObject.put("status", "400");
      jsonObject.put("msg", "请输入邮箱");
      return jsonObject;
    } else if (!sysUser.getEmail().matches("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$")) {
      jsonObject.put("status", "400");
      jsonObject.put("msg", "输入邮箱不正确！");
      return jsonObject;
    }
    jsonObject = userService.insertUser(sysUser);
    if (jsonObject.get("status").equals("200")) {

      emailUtils.registerSucSender(
          sysUser.getEmail(), sysUser.getName(), sysUser.getPassword(), "www.biubiucat.com");
    }
    return jsonObject;
  }
}
