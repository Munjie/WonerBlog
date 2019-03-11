package com.mwj.personweb.controller;

import com.mwj.personweb.service.ICategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** @Auther: munjie @Date: 2019/3/9 19:22 @Description: */
@Controller
public class CategoriesController {

  @Autowired private ICategoryService categoryService;

  /**
   * 获得所有分类名以及每个分类名的文章数目
   *
   * @return
   */
  @GetMapping("/findCategoriesNameAndArticleNum")
  @ResponseBody
  public JSONObject findCategoriesNameAndArticleNum() {
    return categoryService.findCategoriesNameAndArticleNum();
  }

  /**
   * 获得所有的分类
   *
   * @return
   */
  @GetMapping("/findCategoriesName")
  @ResponseBody
  public JSONArray findCategoriesName() {
    return categoryService.findCategoriesName();
  }

  /**
   * 分页获得分类
   *
   * @param rows 一页的大小
   * @param pageNum 当前页
   */
  @PostMapping(value = "/allCategories")
  @ResponseBody
  public JSONArray findAllCategories(String rows, String pageNum) {

    return categoryService.findAllCategories(rows, pageNum);
  }

  /**
   * @param id
   * @return
   */
  @PostMapping(value = "/delete_categories")
  @ResponseBody
  public JSONObject deleteCategories(@RequestParam("id") int id) {

    return categoryService.deleteCategories(id);
  }

  /**
   * @param categoryName
   * @return
   */
  @PostMapping(value = "/add_categories")
  @ResponseBody
  public JSONObject addCategories(@RequestParam("categoryName") String categoryName) {

    return categoryService.addCategories(categoryName);
  }
}
