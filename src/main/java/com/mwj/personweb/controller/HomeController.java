package com.mwj.personweb.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @Author: 母哥 @Date: 2019-03-04 11:28 @Version 1.0
 */
@Controller
public class HomeController {

    private static Logger logger = LoggerFactory.getLogger(BackController.class);

    @RequestMapping("/")
    public String index() {

        return "front/index";
    }

    @RequestMapping("/login.html")
    public String login() {
        return "front/login";
    }

    @RequestMapping("/register.html")
    public String register() {
        return "front/register";
    }

    @PostMapping(value = "/check_edit")
    @ResponseBody
    public JSONObject toEdit(HttpServletRequest request, String name) throws Exception {
        JSONObject object = new JSONObject();
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            object.put("status", "400");
        }

        return object;
    }

    @RequestMapping("/article_edit.html")
    public String articleEdit() {
        return "front/article_edit";
  }
}
