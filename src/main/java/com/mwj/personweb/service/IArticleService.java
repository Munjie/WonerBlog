package com.mwj.personweb.service;

import com.mwj.personweb.model.Article;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/** @Author: 母哥 @Date: 2019-02-15 11:08 @Version 1.0 */
public interface IArticleService {

  /**
   * 分页获得所有文章
   *
   * @param rows 一页显示文章数
   * @param pageNo 第几页
   * @return 该页所有文章
   */
  JSONArray findAllArticles(Authentication authentication, String rows, String pageNo);

  /**
   * 保存文章
   *
   * @param article 文章
   * @return status: 200--成功 500--失败
   */
  JSONObject insertArticle(Article article);

  /**
   * 根据文章id获得文章名
   *
   * @param articleId 文章id
   * @return 文章名
   */
  Map<String, String> showArticleTitleByArticleId(long articleId);

  /**
   * 获得文章
   *
   * @param articleId 文章id
   * @return
   */
  JSONObject getArticleByArticleId(long articleId);

  /**
   * 分页获得该归档日期下的所有文章
   *
   * @param archive 归档日期
   * @param rows 一页大小
   * @param pageNum 页数
   * @return
   */
  JSONObject findArticleByArchive(String archive, int rows, int pageNum);

  /**
   * 计算该归档日期文章的数目
   *
   * @param archive 归档日期
   * @return 该归档日期下文章的数目
   */
  int countArticleArchiveByArchive(String archive);

  /**
   * 计算该分类文章的数目
   *
   * @param category 分类名
   * @return 该分类下文章的数目
   */
  int countArticleCategoryByCategory(String category);
  /**
   * @param
   * @return
   */
  List<Article> newArticle();
  /**
   * @param
   * @return
   */
  Article getArticleById(long articleId);
  /**
   * @param
   * @return
   */
  void updateArticleById(Article article);

  /**
   * 通过id删除文章
   *
   * @param id 文章id
   * @return 1--删除成功 0--删除失败
   */
  Article getArticleByMainId(int id);

  /**
   * 通过id删除文章
   *
   * @param id 文章id
   * @return 1--删除成功 0--删除失败
   */
  @Transactional
  int deleteArticle(int id);

  /**
   * 通过标签分页获得文章部分信息
   *
   * @param tag
   * @return
   */
  JSONObject findArticleByTag(String tag, int rows, int pageNum);
  /**
   * 通过标签获得文章部分信息
   *
   * @param tagName
   * @return
   */
  List<Article> tagArticle(String tagName);

  /**
   * 通过标签获得文章部分信息
   *
   * @param articleId
   * @return
   */
  String findArticleAutor(int articleId);
}
