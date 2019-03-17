package com.mwj.personweb.controller;

import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ISysLogService;
import com.mwj.personweb.service.IVisitService;
import com.mwj.personweb.utils.CommonUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/** @Author: 母哥 @Date: 2019-02-11 10:21 @Version 1.0 */
@RestController
@RequestMapping("/home")
public class IndexController {

  @Autowired private ISysLogService sysLogService;

  @Autowired private IArticleService articleService;

  @Autowired private IVisitService visitorService;

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

  /**
   * 增加访客量
   *
   * @return 网站总访问量以及访客量
   */
  @PostMapping("/getVisitorNumByPageName")
  @ResponseBody
  public void getVisitorNumByPageName(HttpServletRequest request, String pageName) {

    String visit = (String) request.getSession().getAttribute("visit");

    if (StringUtils.isBlank(visit) && StringUtils.isBlank(pageName) && visit == null) {

      String requestHeader = request.getHeader("user-agent");
      if (CommonUtil.isMobileDevice(requestHeader)) {
        visitorService.updateMobile();
      } else {
        visitorService.updatePc();
      }
      visitorService.updateTotal();
      request.getSession().setAttribute("visit", "visited");
    }
    if (StringUtils.isBlank(visit) && StringUtils.isNotBlank(pageName)) {
      visitorService.updateOther();
      request.getSession().setAttribute("visit", "visited");
    }
  }
}
