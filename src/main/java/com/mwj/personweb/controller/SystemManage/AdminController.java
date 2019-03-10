package com.mwj.personweb.controller.SystemManage;

import com.mwj.personweb.controller.BackController;
import com.mwj.personweb.service.IArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/** @Auther: munjie @Date: 2019/3/10 19:33 @Description: */
@Controller
public class AdminController {

  private static Logger logger = LoggerFactory.getLogger(BackController.class);

  @Autowired private IArticleService articleService;

  @GetMapping(value = "/toBack")
  private String toIndex() {

    return "back/index";
  }

  @GetMapping(value = "/article_manage")
  private String articleManage() {

    return "back/article_manage";
  }

  /**
   * 删除文章 post
   *
   * @param cid
   * @param request
   * @return
   */
  @PostMapping(value = "/delete_article")
  @ResponseBody
  public int delete(@RequestParam("cid") int cid, HttpServletRequest request) {

    return articleService.deleteArticle(cid);
  }
}
