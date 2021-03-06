package com.mwj.personweb.controller;

import com.github.pagehelper.PageInfo;
import com.mwj.personweb.bo.CommentBo;
import com.mwj.personweb.model.Article;
import com.mwj.personweb.model.Tags;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ICommentService;
import com.mwj.personweb.service.IMessageService;
import com.mwj.personweb.service.ITagsService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @Author: 母哥 @Date: 2019-02-22 17:21 @Version 1.0 Restful请求 */
@Controller
@RequestMapping("/article")
public class ArticleController {

  private Logger logger = LoggerFactory.getLogger(ArticleController.class);

  @Autowired private IArticleService articleService;

  @Autowired private ITagsService tagService;

  @Autowired private PageUtil pageUtil;

  @Autowired private RedisServer redisServer;

  @Autowired private ICommentService commentService;

  @Autowired private IMessageService messageService;

  @PostMapping(value = "/publish")
  @ResponseBody
  public JSONObject publishArticle(
      Article article, HttpServletRequest request, Authentication authentication) {

    JSONObject returnJson = new JSONObject();

    // 获得文章html代码并生成摘要
    BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
    String articleHtmlContent =
        buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
    article.setArticleTabloid(articleHtmlContent);
    String[] articleTags = request.getParameterValues("articleTagsValue");
    String[] tags = new String[articleTags.length + 1];
    for (int i = 0; i < articleTags.length; i++) {
      // 去掉可能出现的换行符
      articleTags[i] = articleTags[i].replaceAll("<br>", "");
      tags[i] = articleTags[i];
    }
    tags[articleTags.length] = article.getArticleType();
    // 添加标签
    tagService.addTags(tags, 5);
    TimeUtil timeUtil = new TimeUtil();
    String nowDate = timeUtil.getFormatDateForThree();
    long articleId = timeUtil.getLongTime();
    article.setArticleId(articleId);
    article.setPublishDate(nowDate);
    article.setCommentsNum(0);
    article.setArticleTags(CommonUtil.arrayToString(tags));
    article.setAuthor(authentication.getName());
    returnJson = articleService.insertArticle(article);
    return returnJson;
  }

  @GetMapping(value = "/find/{articleId}")
  public String find(
      @PathVariable("articleId") String articleId,
      HttpServletResponse response,
      Model model,
      HttpServletRequest request,
      Authentication authentication)
      throws Exception {
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    request.getSession().removeAttribute("lastUrl");
    List<Tags> tags = null;
    List<Article> articles = null;
    if (authentication != null) {

      messageService.updateMessage(Integer.parseInt(articleId));
    }

    Map<String, String> articleMap =
        articleService.showArticleTitleByArticleId(Long.parseLong(articleId));
    Article articleById = articleService.getArticleById(Long.parseLong(articleId));
    if (articleById != null) {
      String cp = request.getParameter("cp");
      if (StringUtils.isBlank(cp)) {
        cp = "1";
      }
      request.setAttribute("cp", cp);
      PageInfo<CommentBo> comments =
          commentService.getComments(articleById.getId(), Integer.parseInt(cp), 6);
      request.setAttribute("comments", comments);
      request.setAttribute("article", articleById);
    }
    model.addAttribute("articleTitle", articleMap.get("articleTitle"));
    String articleTabloid = articleMap.get("articleTabloid");
    if (redisServer.hasKey("tags")) {
      String tags_temp = redisServer.get("tags");
      tags = JsonUtil.getStringToList(tags_temp, Tags.class);
      logger.info("get tags from redis success!");

    } else {
      tags = tagService.allTags();
      String listToJson = JsonUtil.getListToJson(tags);
      redisServer.set("tags", listToJson);
      logger.info("set tags to redis success!");
    }
    // 从redis获取最新文章
    if (redisServer.hasKey("article_title")) {
      String newArticle = redisServer.get("article_title");
      articles = JsonUtil.getStringToList(newArticle, Article.class);
      logger.info("get article_title from redis success!");

    } else {
      articles = articleService.newArticle();
      String listToJson = JsonUtil.getListToJson(articles);
      redisServer.set("article_title", listToJson);
      logger.info("set article_title to redis success!");
    }
    model.addAttribute("tags", tags);
    model.addAttribute("articles", articles);
    if (articleTabloid.length() <= 110) {
      model.addAttribute("articleTabloid", articleTabloid);
    } else {
      model.addAttribute("articleTabloid", articleTabloid.substring(0, 110));
    }

    // 将文章id存入响应头
    response.setHeader("articleId", articleId);
    return pageUtil.forward(authentication, model, "front/show_article");
  }

  /**
   * 获取文章
   *
   * @param articleId 文章id
   * @return
   */
  @GetMapping(value = "/show/{articleId}")
  public @ResponseBody JSONObject show(@PathVariable("articleId") String articleId) {

    JSONObject jsonObject = articleService.getArticleByArticleId(Long.parseLong(articleId));
    return jsonObject;
  }

  /** 文章编辑本地上传图片 */
  @RequestMapping("/uploadArticleImage")
  public @ResponseBody Map<String, Object> uploadImage(
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam(value = "editormd-image-file", required = true) MultipartFile file) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    try {
      request.setCharacterEncoding("utf-8");
      // 设置返回头后页面才能获取返回url
      response.setHeader("X-Frame-Options", "SAMEORIGIN");
      String fileUrl = FileUtil.upload(file);
      resultMap.put("success", 1);
      resultMap.put("message", "上传成功");
      resultMap.put("url", fileUrl);
    } catch (Exception e) {
      try {
        response.getWriter().write("{\"success\":0}");
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    return resultMap;
  }

  /**
   * 查询文章的评论信息，并补充到里面，返回前端
   *
   * @param request
   * @param id
   */
  private void completeArticle(HttpServletRequest request, int id) {

    /*String cp = request.getParameter("cp");
    if (StringUtils.isBlank(cp)) {
      cp = "1";
    }
    request.setAttribute("cp", cp);
    PageInfo<CommentBo> commentsPaginator = commentService.getComments(id, Integer.parseInt(cp), 6);
    request.setAttribute("comments", commentsPaginator);*/
  }
}
