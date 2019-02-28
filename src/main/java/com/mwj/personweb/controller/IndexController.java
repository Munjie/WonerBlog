package com.mwj.personweb.controller;

import com.google.gson.Gson;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ISysLogService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/** @Author: 母哥 @Date: 2019-02-11 10:21 @Version 1.0 */
@RestController
public class IndexController {

  @Autowired private ISysLogService sysLogService;

  @Autowired private IArticleService articleService;

  public static String accessKey = "b-lHQMCnIt3TO4P7QL1hVM06sm2cxAqN-53uwWDe";
  public static String secretKey = "mSll8IcTHHotFymXpvrkPETXwPm9sfUrhn_VtWF_";
  public static String bucket = "munjie";
  private static String QINIU_IMAGE_DOMAIN = "http://cdn.biubiucat.com";

  //		华东	Zone.zone0()
  //		华北	Zone.zone1()
  //		华南	Zone.zone2()
  //		北美	Zone.zoneNa0()

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
  @RequestMapping("/myArticles")
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
      String fileUrl = upload(file);
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

  // 文件流上传
  public static String upload(MultipartFile file) throws IOException {
    // 构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Zone.zone0());
    UploadManager uploadManager = new UploadManager(cfg);
    // 默认不指定key的情况下，以文件内容的hash值作为文件名
    String key = null;
    DefaultPutRet putRet = null;
    try {
      byte[] uploadBytes = file.getBytes();
      ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
      Auth auth = Auth.create(accessKey, secretKey);
      String upToken = auth.uploadToken(bucket);
      try {
        Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
        putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
      } catch (QiniuException ex) {
        Response r = ex.response;
        System.err.println(r.toString());
        try {
          System.err.println(r.bodyString());
        } catch (QiniuException ex2) {

        }
      }
    } catch (UnsupportedEncodingException ex) {
    }
    return QINIU_IMAGE_DOMAIN + "/" + putRet.key;
  }
}
