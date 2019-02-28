package com.mwj.personweb.service.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mwj.personweb.dao.IArtcileDao;
import com.mwj.personweb.model.Article;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @Author: 母哥 @Date: 2019-02-15 11:23 @Version 1.0 */
@Service
public class ArticleServiceImpl implements IArticleService {

  private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

  private static final String ARTICLE_KEY = "ARTICLE_KEY";

  @Autowired private IArtcileDao artcileDao;

  @Autowired private RedisServer redisServer;

  @Override
  public JSONArray findAllArticles(String rows, String pageNo) {

    com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
    int pageNum = Integer.parseInt(pageNo);
    int pageSize = Integer.parseInt(rows);

    PageHelper.startPage(pageNum, pageSize);
    List<Article> articles = artcileDao.findAllArticles();
    PageInfo<Article> pageInfo = new PageInfo<>(articles);
    List<Map<String, Object>> newArticles = new ArrayList<>();
    Map<String, Object> map;

    for (Article article : articles) {
      map = new HashMap<>();
      map.put("articleUrl", "/showArticle?articleId=" + article.getArticleId());
      map.put("articleTitle", article.getArticleTitle());
      map.put("articleType", article.getArticleType());
      map.put("publishDate", article.getPublishDate());
      map.put("originalAuthor", article.getOriginalAuthor());
      map.put("articleTabloid", article.getArticleTabloid());
      map.put("articleImg", randomPath());
      newArticles.add(map);
    }
    JSONArray jsonArray = JSONArray.fromObject(newArticles);
    Map<String, Object> thisPageInfo = new HashMap<>();
    thisPageInfo.put("pageNum", pageInfo.getPageNum());
    thisPageInfo.put("pageSize", pageInfo.getPageSize());
    thisPageInfo.put("total", pageInfo.getTotal());
    thisPageInfo.put("pages", pageInfo.getPages());
    thisPageInfo.put("isFirstPage", pageInfo.isIsFirstPage());
    thisPageInfo.put("isLastPage", pageInfo.isIsLastPage());

    jsonArray.add(thisPageInfo);
    //    logger.info(jsonArray.toString());
    return jsonArray;
  }

  @Override
  public JSONObject insertArticle(Article article) {
    JSONObject articleReturn = new JSONObject();

    try {

      String articleUrl =
          "https://www.biubiucat.com/showArticle?articleId=" + article.getArticleId();
      article.setArticleUrl(articleUrl);

      int i = artcileDao.insertArticle(article);

      articleReturn.put("status", 200);
      articleReturn.put("articleTitle", article.getArticleTitle());
      articleReturn.put("author", article.getOriginalAuthor());
      // 本博客中的URL
      articleReturn.put(
          "articleUrl",
          "/findArticle?articleId="
              + article.getArticleId()
              + "&originalAuthor="
              + article.getOriginalAuthor());
      return articleReturn;
    } catch (Exception e) {
      articleReturn.put("status", 500);
      logger.error("用户 " + article.getAuthor() + " 保存文章 " + article.getArticleTitle() + " 失败");
      e.printStackTrace();
      return articleReturn;
    }
  }

  @Override
  public Map<String, String> showArticleTitleByArticleId(long articleId) {
    Article articleInfo = null;
    String s = redisServer.get(String.valueOf(articleId));
    if (StringUtils.isNotBlank(s)) {
      articleInfo = JsonUtil.getStringToObject(s, Article.class);
      logger.info("从redis缓存获取文章名成功");
    } else {
      articleInfo = artcileDao.showArticleTitleDetailByArticleId(articleId);
      redisServer.set(String.valueOf(articleId), JsonUtil.getObjectToJson(articleInfo));
      redisServer.expire(String.valueOf(articleId), 2000);
      logger.info("设置redis缓存文章名成功");
    }
    Map<String, String> articleMap = new HashMap<>();
    articleMap.put("articleTitle", articleInfo.getArticleTitle());
    articleMap.put("articleTabloid", articleInfo.getArticleTabloid());
    return articleMap;
  }

  @Override
  public JSONObject getArticleByArticleId(long articleId) {

    Article article = null;
    String s = redisServer.get(String.valueOf(articleId));
    if (StringUtils.isNotBlank(s)) {
      article = JsonUtil.getStringToObject(s, Article.class);
      logger.info("从redis缓存获取文章详细信息成功");
    } else {
      article = artcileDao.showArticleTitleDetailByArticleId(articleId);
      redisServer.set(String.valueOf(articleId), JsonUtil.getObjectToJson(article));
      redisServer.expire(String.valueOf(articleId), 2000);
      logger.info("设置redis缓存文章详细信息成功");
    }

    JSONObject jsonObject = new JSONObject();
    if (article != null) {
      jsonObject.put("status", "200");
      jsonObject.put("author", article.getAuthor());
      jsonObject.put("articleId", articleId);
      jsonObject.put("originalAuthor", article.getOriginalAuthor());
      jsonObject.put("articleTitle", article.getArticleTitle());
      jsonObject.put("publishDate", article.getPublishDate());
      jsonObject.put("articleContent", article.getArticleContent());
      jsonObject.put("articleType", article.getArticleType());
      return jsonObject;
    } else {
      jsonObject.put("status", "500");
      jsonObject.put("errorInfo", "获取文章信息失败");
      logger.error("获取文章id " + articleId + " 失败");
      return jsonObject;
    }
  }

  public static String randomPath() {
    int random = (int) (Math.random() * 10 + 1);
    String path = "i/f" + random + ".jpg";
    return path;
  }
}
