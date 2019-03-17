package com.mwj.personweb.service.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mwj.personweb.dao.IArtcileDao;
import com.mwj.personweb.model.Article;
import com.mwj.personweb.service.IArchiveService;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ICommentReplyService;
import com.mwj.personweb.service.ICommentService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.CommonUtil;
import com.mwj.personweb.utils.JsonUtil;
import com.mwj.personweb.utils.StringAndArray;
import com.mwj.personweb.utils.TimeUtil;
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

  @Autowired private IArchiveService archiveService;

  @Autowired private ICommentService commentService;

  @Autowired private ICommentReplyService commentReplyService;

  @Override
  public JSONArray findAllArticles(String rows, String pageNo) {

    int pageNum = Integer.parseInt(pageNo);
    int pageSize = Integer.parseInt(rows);

    PageHelper.startPage(pageNum, pageSize);
    List<Article> articles = artcileDao.findAllArticles();
    PageInfo<Article> pageInfo = new PageInfo<>(articles);
    List<Map<String, Object>> newArticles = new ArrayList<>();
    Map<String, Object> map;

    for (Article article : articles) {
      map = new HashMap<>();
      map.put("articleUrl", "/article/find/" + article.getArticleId());
      map.put("articleTitle", article.getArticleTitle());
      map.put("articleType", article.getArticleType());
      map.put("publishDate", article.getPublishDate());
      map.put("author", article.getAuthor());
      map.put("articleTabloid", article.getArticleTabloid());
      map.put("articleImg", randomPath());
      map.put("likes", article.getLikes());
      map.put("commentsNum", article.getCommentsNum());
      map.put("id", article.getId());
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

      // 判断发表文章的归档日期是否存在，不存在则插入归档日期
      String archiveName = TimeUtil.timeWhippletreeToYear(article.getPublishDate().substring(0, 7));
      archiveService.addArchiveName(archiveName);
      articleReturn.put("status", 200);
      articleReturn.put("articleTitle", article.getArticleTitle());
      articleReturn.put("author", article.getOriginalAuthor());
      // 本博客中的URL
      articleReturn.put("articleUrl", "/article/find/" + article.getArticleId());
      return articleReturn;
    } catch (Exception e) {
      articleReturn.put("status", 500);
      logger.error("用户 " + article.getAuthor() + " 保存文章 " + article.getArticleTitle() + " 失败", e);
      return articleReturn;
    }
  }

  @Override
  public Map<String, String> showArticleTitleByArticleId(long articleId) {
    Article articleInfo = null;
    String s = redisServer.get(String.valueOf(articleId));
    if (StringUtils.isNotBlank(s)) {
      articleInfo = JsonUtil.getStringToObject(s, Article.class);
      logger.info("get article detail from redis success!");
    } else {
      articleInfo = artcileDao.showArticleTitleDetailByArticleId(articleId);
      redisServer.set(String.valueOf(articleId), JsonUtil.getObjectToJson(articleInfo));
      redisServer.expire(String.valueOf(articleId), 2000);
      logger.info("set article detail from redis success!");
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
      logger.info("Get article detail from redis success!");
    } else {
      article = artcileDao.showArticleTitleDetailByArticleId(articleId);
      redisServer.set(String.valueOf(articleId), JsonUtil.getObjectToJson(article));
      redisServer.expire(String.valueOf(articleId), 2000);
      logger.info("Set article detail from redis success!");
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

  @Override
  public JSONObject findArticleByArchive(String archive, int rows, int pageNum) {
    List<Article> articles;
    PageInfo<Article> pageInfo;
    JSONArray articleJsonArray = new JSONArray();
    String showMonth = "hide";
    if (!"".equals(archive)) {
      archive = TimeUtil.timeYearToWhippletree(archive);
    }
    PageHelper.startPage(pageNum, rows);
    if ("".equals(archive)) {
      articles = artcileDao.findAllArticlesPartInfo();
    } else {
      articles = artcileDao.findArticleByArchive(archive);
      showMonth = "show";
    }
    pageInfo = new PageInfo<>(articles);

    articleJsonArray = timeLineReturn(articleJsonArray, articles);

    JSONObject pageJson = new JSONObject();
    pageJson.put("pageNum", pageInfo.getPageNum());
    pageJson.put("pageSize", pageInfo.getPageSize());
    pageJson.put("total", pageInfo.getTotal());
    pageJson.put("pages", pageInfo.getPages());
    pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
    pageJson.put("isLastPage", pageInfo.isIsLastPage());

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", 200);
    jsonObject.put("result", articleJsonArray);
    jsonObject.put("pageInfo", pageJson);
    jsonObject.put("articleNum", artcileDao.countArticles());
    jsonObject.put("showMonth", showMonth);

    return jsonObject;
  }

  @Override
  public int countArticleArchiveByArchive(String archive) {

    int i = artcileDao.countArticleArchiveByArchive(archive);
    return i;
  }

  @Override
  public int countArticleCategoryByCategory(String category) {
    return artcileDao.countArticleCategoryByCategory(category);
  }

  @Override
  public List<Article> newArticle() {
    return artcileDao.newArticle();
  }

  @Override
  public Article getArticleById(long articleId) {
    return artcileDao.showArticleTitleDetailByArticleId(articleId);
  }

  @Override
  public void updateArticleById(Article article) {
    artcileDao.updateArticleById(article);
  }

  @Override
  public Article getArticleByMainId(int id) {
    return artcileDao.findArticleByMainId(id);
  }

  @Override
  public int deleteArticle(int id) {

    try {
      artcileDao.deleteArticleById(id);
      commentService.deleteCommentsById(id);
      commentReplyService.deleteReplyByCid(id);
    } catch (Exception e) {
      logger.error("删除文章" + id + "失败");
      return 0;
    }
    return 1;
  }

  @Override
  public JSONObject findArticleByTag(String tag, int rows, int pageNum) {
    PageHelper.startPage(pageNum, rows);
    List<Article> articles = artcileDao.findArticleByTag(tag);
    PageInfo<Article> pageInfo = new PageInfo<>(articles);
    JSONObject articleJson;
    JSONArray articleJsonArray = new JSONArray();
    // 二次判断标签是否匹配
    for (Article article : articles) {
      String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
      for (String str : tagsArray) {
        if (str.equals(tag)) {
          articleJson = new JSONObject();
          articleJson.put("articleId", article.getArticleId());
          articleJson.put("originalAuthor", article.getOriginalAuthor());
          articleJson.put("articleTitle", article.getArticleTitle());
          articleJson.put("articleCategories", article.getArticleCategories());
          articleJson.put("publishDate", article.getPublishDate());
          articleJson.put("articleTags", tagsArray);
          articleJsonArray.add(articleJson);
        }
      }
    }

    JSONObject pageJson = new JSONObject();
    pageJson.put("pageNum", pageInfo.getPageNum());
    pageJson.put("pageSize", pageInfo.getPageSize());
    pageJson.put("total", pageInfo.getTotal());
    pageJson.put("pages", pageInfo.getPages());
    pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
    pageJson.put("isLastPage", pageInfo.isIsLastPage());

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", 201);
    jsonObject.put("result", articleJsonArray);
    jsonObject.put("tag", tag);
    jsonObject.put("pageInfo", pageJson);
    return jsonObject;
  }

  @Override
  public List<Article> tagArticle(String tagName) {
    List<Article> articleByTag = null;
    if (redisServer.hasKey(tagName)) {
      String s = redisServer.get(tagName);
      articleByTag = JsonUtil.getStringToList(s, Article.class);
      logger.info("get article by tagname from redis success!");
    } else {

      articleByTag = artcileDao.findArticleByTag(tagName);
      redisServer.set(tagName, JsonUtil.getListToJson(articleByTag));
      logger.info("set article by tagname from redis success!");
    }
    return articleByTag;
  }

  public static String randomPath() {
    int random = (int) (Math.random() * 13 + 1);
    String path = "i/f" + random + ".jpg";
    return path;
  }

  public JSONArray timeLineReturn(JSONArray articleJsonArray, List<Article> articles) {
    JSONObject articleJson;
    for (Article article : articles) {
      String[] tagsArray = CommonUtil.stringToArray(article.getArticleTags());
      articleJson = new JSONObject();
      articleJson.put("articleId", article.getArticleId());
      articleJson.put("originalAuthor", article.getOriginalAuthor());
      articleJson.put("articleTitle", article.getArticleTitle());
      articleJson.put("articleCategories", article.getArticleCategories());
      articleJson.put("publishDate", article.getPublishDate());
      articleJson.put("articleTags", tagsArray);
      articleJsonArray.add(articleJson);
    }
    return articleJsonArray;
  }

  @Override
  public String findArticleAutor(int id) {
    return artcileDao.findArticleAutor(id);
  }
}
