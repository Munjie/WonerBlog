package com.mwj.personweb.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 母哥 @Date: 2019-02-28 11:08 @Version 1.0
 */
@RequestMapping("/admin")
@Controller
public class BackController {

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
    @PreAuthorize("hasRole('ROLE_USER')")
    public String loginSuccess(HttpServletRequest request) {

        return "back/index";
    }

    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException)
                        request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
  }
}
