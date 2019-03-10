package com.mwj.personweb.controller.SystemManage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** @Auther: munjie @Date: 2019/3/10 19:33 @Description: */
@Controller
public class AdminController {

  @GetMapping(value = "/toBack")
  private String toIndex() {

    return "back/index";
  }

  @GetMapping(value = "/article_manage")
  private String articleManage() {

    return "back/article_manage";
  }
}
