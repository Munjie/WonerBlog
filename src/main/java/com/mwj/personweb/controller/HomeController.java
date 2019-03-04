package com.mwj.personweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
