package com.mwj.personweb.service.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mwj.personweb.dao.ICategoryDao;
import com.mwj.personweb.model.Category;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ICategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Override
  public JSONArray findAllCategories(String rows, String pageNo) {
    int pageNum = Integer.parseInt(pageNo);
    int pageSize = Integer.parseInt(rows);
    List<Category> allCategory = categoryDao.findAllCategory();
    PageHelper.startPage(pageNum, pageSize);

    PageInfo<Category> pageInfo = new PageInfo<>(allCategory);
    List<Map<String, Object>> newCategory = new ArrayList<>();
    Map<String, Object> map;

    for (Category category : allCategory) {
      map = new HashMap<>();
      map.put("id", category.getId());
      map.put("categoryName", category.getCategoryName());
      newCategory.add(map);
    }
    JSONArray jsonArray = JSONArray.fromObject(newCategory);
    Map<String, Object> thisPageInfo = new HashMap<>();
    thisPageInfo.put("pageNum", pageInfo.getPageNum());
    thisPageInfo.put("pageSize", pageInfo.getPageSize());
    thisPageInfo.put("total", pageInfo.getTotal());
    thisPageInfo.put("pages", pageInfo.getPages());
    thisPageInfo.put("isFirstPage", pageInfo.isIsFirstPage());
    thisPageInfo.put("isLastPage", pageInfo.isIsLastPage());

    jsonArray.add(thisPageInfo);
    return jsonArray;
  }

  @Override
  public JSONObject deleteCategories(int id) {
    JSONObject returnJson = new JSONObject();
    try {
      categoryDao.deleteCategory(id);
      returnJson.put("status", 200);
    } catch (Exception e) {

      returnJson.put("status", 400);
    }
    return returnJson;
  }

  @Override
  public JSONObject addCategories(String categoryName) {
    JSONObject returnJson = new JSONObject();
    try {
      Category category = new Category();
      category.setCategoryName(categoryName);
      categoryDao.addCategories(category);
      returnJson.put("status", 200);
    } catch (Exception e) {

      returnJson.put("status", 400);
    }
    return returnJson;
  }
}
