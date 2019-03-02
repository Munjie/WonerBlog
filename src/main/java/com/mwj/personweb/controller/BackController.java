package com.mwj.personweb.controller;

import com.mwj.personweb.exception.MyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** @Author: 母哥 @Date: 2019-02-28 11:08 @Version 1.0 */
@RequestMapping("/admin")
@Controller
public class BackController {

  @Autowired private SessionRegistry sessionRegistry;

  @GetMapping(value = "/toLogin")
  public String backLogin() {
    return "back/login";
  }

  @RequestMapping("/user")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_USER')")
  public String printUser() {
    return "如果你看见这句话，说明你有ROLE_USER角色";
  }

  @RequestMapping(value = "/login/success")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String loginSuccess(HttpServletRequest request) {

    return "back/index";
  }

  @RequestMapping("/login")
  public String login() {

    return "back/login";
  }

  @RequestMapping("/invalid")
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public String invalid() {
    return "Session 已过期，请重新登录";
  }

  @RequestMapping("/login/error")
  public String loginError(HttpServletRequest request, HttpServletResponse response, Model model) {
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
    return "back/login";
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
}
