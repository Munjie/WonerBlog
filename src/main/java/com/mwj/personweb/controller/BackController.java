package com.mwj.personweb.controller;

import com.mwj.personweb.utils.CodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping(value = "/login")
    public String login(HttpServletRequest request) {

        if (!CodeUtil.checkVerifyCode(request)) {

            return "验证码有误！";
        } else {
            return "back/login";
        }
    }
}
