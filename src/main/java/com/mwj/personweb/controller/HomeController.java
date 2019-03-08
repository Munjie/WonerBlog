package com.mwj.personweb.controller;

import com.mwj.personweb.utils.PageUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * @Author: 母哥 @Date: 2019-03-04 11:28 @Version 1.0
 */
@Controller
public class HomeController {
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private PageUtil pageUtil;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) throws Exception {
        return pageUtil.forward(authentication, model, "front/index");
    }

    @GetMapping("/login.html")
    public String login() {
        return "front/login";
    }

    @GetMapping("/register.html")
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

    @GetMapping("/article_edit.html")
    public String articleEdit(Authentication authentication, Model model) throws Exception {
        return pageUtil.forward(authentication, model, "front/article_edit");
    }

    @GetMapping("/archives")
    public String archives(HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String archive = request.getParameter("archive");

        try {
            response.setHeader("archive", archive);
        } catch (Exception e) {
        }
        return "front/archives_article";
    }
}
