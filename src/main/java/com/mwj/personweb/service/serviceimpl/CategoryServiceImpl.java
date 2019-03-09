package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ICategoryDao;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ICategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/9 19:12 @Description: */
@Service
public class CategoryServiceImpl implements ICategoryService {

  @Autowired private ICategoryDao categoryDao;

  @Autowired private IArticleService articleService;

  @Override
  public JSONObject findCategoriesNameAndArticleNum() {
    List<String> categoryNames = categoryDao.findCategoriesName();
    JSONObject categoryJson;
    JSONArray categoryJsonArray = new JSONArray();
    JSONObject returnJson = new JSONObject();
    for (String categoryName : categoryNames) {
      categoryJson = new JSONObject();
      categoryJson.put("categoryName", categoryName);
      categoryJson.put(
          "categoryArticleNum", articleService.countArticleCategoryByCategory(categoryName));
      categoryJsonArray.add(categoryJson);
    }
    returnJson.put("status", 200);
    returnJson.put("result", categoryJsonArray);
    return returnJson;
  }

  @Override
  public JSONArray findCategoriesName() {
    List<String> categoryNames = categoryDao.findCategoriesName();
    return JSONArray.fromObject(categoryNames);
  }

  @Override
  public int countCategoriesNum() {
    return categoryDao.countCategoriesNum();
  }
}
