package com.mwj.personweb.controller;

import com.mwj.personweb.model.Article;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.utils.BuildArticleTabloidUtil;
import com.mwj.personweb.utils.TimeUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: 母哥 @Date: 2019-02-22 17:21 @Version 1.0 Restful请求
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

  @Autowired private IArticleService articleService;

    @PostMapping(value = "/publish")
  @ResponseBody
  public JSONObject publishArticle(Article article, HttpServletRequest request) {

    JSONObject returnJson = new JSONObject();

    // 获得文章html代码并生成摘要
    BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
    String articleHtmlContent =
        buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
    article.setArticleTabloid(articleHtmlContent);
    TimeUtil timeUtil = new TimeUtil();
    String nowDate = timeUtil.getFormatDateForThree();
    long articleId = timeUtil.getLongTime();
    article.setAuthor(article.getOriginalAuthor());
    article.setArticleId(articleId);
    article.setPublishDate(nowDate);
    returnJson = articleService.insertArticle(article);
    return returnJson;
    }

    @GetMapping(value = "/find/{articleId}")
    public String find(
            @PathVariable("articleId") String articleId,
      HttpServletResponse response,
      Model model,
      HttpServletRequest request) {
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    request.getSession().removeAttribute("lastUrl");

    Map<String, String> articleMap =
        articleService.showArticleTitleByArticleId(Long.parseLong(articleId));
    model.addAttribute("articleTitle", articleMap.get("articleTitle"));
    String articleTabloid = articleMap.get("articleTabloid");
    if (articleTabloid.length() <= 110) {
      model.addAttribute("articleTabloid", articleTabloid);
    } else {
      model.addAttribute("articleTabloid", articleTabloid.substring(0, 110));
    }

    // 将文章id存入响应头
    response.setHeader("articleId", articleId);
        return "front/show_article";
  }

  /**
   * 获取文章
   *
   * @param articleId 文章id
   * @return
   */
  @GetMapping(value = "/show/{articleId}")
  public @ResponseBody
  JSONObject show(@PathVariable("articleId") String articleId) {

    JSONObject jsonObject = articleService.getArticleByArticleId(Long.parseLong(articleId));
    return jsonObject;
  }
}
