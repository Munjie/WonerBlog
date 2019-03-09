package com.mwj.personweb.controller;

import com.mwj.personweb.service.ICategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** @Auther: munjie @Date: 2019/3/9 19:22 @Description: */
@RestController
public class CategoriesController {

  @Autowired private ICategoryService categoryService;

  /**
   * 获得所有分类名以及每个分类名的文章数目
   *
   * @return
   */
  @GetMapping("/findCategoriesNameAndArticleNum")
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
}
