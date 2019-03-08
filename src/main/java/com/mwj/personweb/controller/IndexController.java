package com.mwj.personweb.controller;

import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ISysLogService;
import com.mwj.personweb.utils.FileUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

  /** 文章编辑本地上传图片 */
  @RequestMapping("/uploadImage")
  public @ResponseBody Map<String, Object> uploadImage(
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam(value = "editormd-image-file", required = true) MultipartFile file) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    try {
      request.setCharacterEncoding("utf-8");
      // 设置返回头后页面才能获取返回url
      response.setHeader("X-Frame-Options", "SAMEORIGIN");
      String fileUrl = FileUtil.upload(file);
      resultMap.put("success", 1);
      resultMap.put("message", "上传成功");
      resultMap.put("url", fileUrl);
    } catch (Exception e) {
      try {
        response.getWriter().write("{\"success\":0}");
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    return resultMap;
  }
}
