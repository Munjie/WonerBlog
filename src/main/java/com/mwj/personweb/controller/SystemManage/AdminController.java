package com.mwj.personweb.controller.SystemManage;

import com.mwj.personweb.controller.BackController;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** @Auther: munjie @Date: 2019/3/10 19:33 @Description: */
@Controller
public class AdminController {

  private static Logger logger = LoggerFactory.getLogger(BackController.class);

  @Autowired private IArticleService articleService;

  @Autowired private PageUtil pageUtil;

  @GetMapping(value = "/toBack")
  private String toIndex(
      Authentication authentication, HttpServletRequest httpServletRequest, Model model)
      throws Exception {
    HttpSession session = httpServletRequest.getSession();
    Object count = session.getServletContext().getAttribute("count");
    return pageUtil.forward(authentication, model, "back/index");
  }
  /**
   * @description //文章管理
   * @param:
   * @return:
   * @date: 2019/3/11 10:23
   */
  @GetMapping(value = "/article_manage")
  private String articleManage(Authentication authentication, Model model) throws Exception {
    return pageUtil.forward(authentication, model, "back/article_manage");
  }

  /**
   * @description //评论管理
   * @param:
   * @return:
   * @date: 2019/3/11 10:25
   */
  @GetMapping(value = "/comments_manage")
  private String commentsManage(Authentication authentication, Model model) throws Exception {
    return pageUtil.forward(authentication, model, "back/comments_manage");
  }

  /**
   * @description //分类管理
   * @param:
   * @return:
   * @date: 2019/3/11 10:24
   */
  @GetMapping(value = "/categories_manage")
  private String categoriesManage(Authentication authentication, Model model) throws Exception {
    return pageUtil.forward(authentication, model, "back/categories_manage");
  }

  /**
   * @description //标签管理
   * @param:
   * @return:
   * @date: 2019/3/11 10:22
   */
  @GetMapping(value = "/tags_manage")
  private String tagsManage(Authentication authentication, Model model) throws Exception {
    return pageUtil.forward(authentication, model, "back/tags_manage");
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
  /**
   * @description //用户信息
   * @param:
   * @return:
   * @date: 2019/3/11 10:22
   */
  @GetMapping(value = "/user_manage")
  private String userInfo(Authentication authentication, Model model) throws Exception {
    return pageUtil.forward(authentication, model, "back/user_info");
  }
}
