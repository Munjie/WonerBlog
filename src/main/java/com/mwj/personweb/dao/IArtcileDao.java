package com.mwj.personweb.dao;

import com.mwj.personweb.model.Article;

import java.util.List;

/** @Author: 母哥 @Date: 2019-02-13 15:23 @Version 1.0 */
public interface IArtcileDao {

  List<Article> findAllArticles();

  int insertArticle(Article article);

  Article showArticleTitleDetailByArticleId(long articleId);
}
