package com.mwj.personweb.controller;

import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ISysLogService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @Author: 母哥 @Date: 2019-02-11 10:21 @Version 1.0 */
@RestController
@RequestMapping("/home")
public class IndexController {

  @Autowired private ISysLogService sysLogService;

  @Autowired private IArticleService articleService;

  @GetMapping("/find")
  public String find() {
    //    JSONObject object = new JSONObject();
    //    List<SysLog> sysLogs = sysLogService.listAllLog();
    //    JSONArray jsonArray = new JSONArray();
    //    for (SysLog syslog : sysLogs) {
    //
    //      JSONObject jsonObject = new JSONObject();
    //      jsonObject.put("ip", syslog.getIp());
    //      jsonArray.put(jsonObject);
    //    }
    //    object.put("list", jsonArray);
    //    return object.toString();
    return null;
  }

  /**
   * 分页获得当前页文章
   *
   * @param rows 一页的大小
   * @param pageNum 当前页
   */
  @PostMapping(value = "/allArticle")
  public JSONArray myArticles(String rows, String pageNum) {

    return articleService.findAllArticles(rows, pageNum);
  }
}
