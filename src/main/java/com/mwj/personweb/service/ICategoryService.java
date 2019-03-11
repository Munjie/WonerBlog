package com.mwj.personweb.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** @Auther: munjie @Date: 2019/3/9 19:11 @Description: */
public interface ICategoryService {

  /**
   * 获得所有的分类以及该分类的文章总数
   *
   * @return
   */
  JSONObject findCategoriesNameAndArticleNum();

  /**
   * 获得所有的分类
   *
   * @return
   */
  JSONArray findCategoriesName();

  /**
   * 获得分类数目
   *
   * @return
   */
  int countCategoriesNum();

  /**
   * @description //TODO
   * @param:
   * @return:
   * @date: 2019/3/11 10:44
   */
  JSONArray findAllCategories(String rows, String pageNo);
  /**
   * @description //TODO
   * @param:
   * @return:
   * @date: 2019/3/11 11:11
   */
  JSONObject deleteCategories(int id);

  JSONObject addCategories(String categoryName);
}
