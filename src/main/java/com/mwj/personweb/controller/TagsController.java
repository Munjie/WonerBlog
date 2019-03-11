package com.mwj.personweb.controller;

import com.mwj.personweb.service.ITagsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/** @Auther: munjie @Date: 2019/3/11 15:30 @Description: */
@Controller
public class TagsController {

  @Autowired private ITagsService tagsService;
  /**
   * @param rows 一页的大小
   * @param pageNum 当前页
   */
  @PostMapping(value = "/allTags")
  @ResponseBody
  public JSONArray findAllTags(String rows, String pageNum) {

    return tagsService.findAllTags(rows, pageNum);
  }
  /**
   * @description //批量删除
   * @param:
   * @return:
   * @date: 2019/3/11 17:47
   */
  @PostMapping(value = "/bachDeleteTags")
  @ResponseBody
  public JSONObject bachDeleteTags(String delitems) {

    List<String> delList = new ArrayList<String>();
    String[] strs = delitems.split(",");
    for (String s : strs) {

      delList.add(s);
    }
    return tagsService.bachDeleteTags(delList);
  }
  /**
   * @description //删除单个tag
   * @param:
   * @return:
   * @date: 2019/3/11 17:47
   */
  @PostMapping(value = "/delete_tag")
  @ResponseBody
  public JSONObject deleteTags(int id) {

    return tagsService.deleteTags(id);
  }
}
