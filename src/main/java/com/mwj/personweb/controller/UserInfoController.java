package com.mwj.personweb.controller;

import com.mwj.personweb.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: 母哥 @Date: 2019-03-08 10:02 @Version 1.0
 */
@Controller
public class UserInfoController {

    private static Logger logger = LoggerFactory.getLogger(BackController.class);

    @Autowired
    private PageUtil pageUtil;

    @GetMapping(value = "/userInfo")
    public String getUserInfo(Authentication authentication, Model model) throws Exception {

        return pageUtil.forward(authentication, model, "front/user_info");
    }
}
