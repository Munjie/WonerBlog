package com.mwj.personweb.dao;

import com.mwj.personweb.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** @Author: 母哥 @Date: 2019-02-13 15:23 @Version 1.0 */
public interface IArtcileDao {

  List<Article> findAllArticles();

  int insertArticle(Article article);

  Article showArticleTitleDetailByArticleId(long articleId);

  List<Article> findAllArticlesPartInfo();

  List<Article> findArticleByArchive(@Param("archive") String archive);

  int countArticles();

  int countArticleArchiveByArchive(@Param("archive") String archive);

  int countArticleCategoryByCategory(@Param("category") String category);

  List<Article> newArticle();

  void updateArticleById(Article article);

  Article findArticleByMainId(int id);

  int deleteArticleById(int id);

  List<Article> findArticleByTag(@Param("tag") String tag);
}
