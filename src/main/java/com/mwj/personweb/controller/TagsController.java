package com.mwj.personweb.controller;

import com.mwj.personweb.model.Article;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ITagsService;
import com.mwj.personweb.utils.CommonUtil;
import com.mwj.personweb.utils.PageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/** @Auther: munjie @Date: 2019/3/11 15:30 @Description: */
@Controller
public class TagsController {

  @Autowired private ITagsService tagsService;

  @Autowired private IArticleService articleService;

  @Autowired private PageUtil pageUtil;
  /**
   * @param rows 一页的大小
   * @param pageNum 当前页
   */
  @PostMapping(value = "/allTags")
  @ResponseBody
  public JSONArray findAllTags(String rows, String pageNum) {

    return tagsService.findAllTags(rows, pageNum);
  }
  /**
   * @description //批量删除
   * @param:
   * @return:
   * @date: 2019/3/11 17:47
   */
  @PostMapping(value = "/bachDeleteTags")
  @ResponseBody
  public JSONObject bachDeleteTags(String delitems) {

    List<String> delList = new ArrayList<String>();
    String[] strs = delitems.split(",");
    for (String s : strs) {

      delList.add(s);
    }
    return tagsService.bachDeleteTags(delList);
  }
  /**
   * @description //删除单个tag
   * @param:
   * @return:
   * @date: 2019/3/11 17:47
   */
  @PostMapping(value = "/delete_tag")
  @ResponseBody
  public JSONObject deleteTags(int id) {

    return tagsService.deleteTags(id);
  }

  @PostMapping("/getTagArticle")
  public JSONObject getTagArticle(@RequestParam("tag") String tag, HttpServletRequest request) {
    try {
      tag = CommonUtil.unicodeToString(tag);
    } catch (Exception e) {
    }
    if ("".equals(tag)) {
      return tagsService.findTagsCloud();
    } else {
      int rows = Integer.parseInt(request.getParameter("rows"));
      int pageNum = Integer.parseInt(request.getParameter("pageNum"));
      return articleService.findArticleByTag(tag, rows, pageNum);
    }
  }

  /**
   * 获取文章
   *
   * @param tagName
   * @return
   */
  @GetMapping(value = "/tagArticle/{tagName}")
  public String show(
      @PathVariable("tagName") String tagName, Model model, Authentication authentication)
      throws Exception {

    List<Article> articles = articleService.tagArticle(tagName);

    model.addAttribute("num", articles.size());
    model.addAttribute("articles", articles);
    model.addAttribute("tagName", tagName);

    return pageUtil.forward(authentication, model, "front/tag_article");
  }
}
