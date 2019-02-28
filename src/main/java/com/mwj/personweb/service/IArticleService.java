package com.mwj.personweb.service;

import com.mwj.personweb.model.Article;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
  JSONArray findAllArticles(String rows, String pageNo);

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
}
