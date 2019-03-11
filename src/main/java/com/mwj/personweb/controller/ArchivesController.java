package com.mwj.personweb.controller;

import com.mwj.personweb.service.IArchiveService;
import com.mwj.personweb.service.IArticleService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/** @Author: 母哥 @Date: 2019-03-08 16:01 @Version 1.0 */
@RestController
public class ArchivesController {

  private static Logger logger = LoggerFactory.getLogger(ArchivesController.class);

  @Autowired private IArchiveService archiveService;
  @Autowired private IArticleService articleService;

  /**
   * 获得所有归档日期以及每个归档日期的文章数目
   *
   * @return
   */
  @GetMapping(value = "/findArchiveNameAndArticleNum")
  public JSONObject findArchiveNameAndArticleNum() {
    return archiveService.findArchiveNameAndArticleNum();
  }

  /**
   * 分页获得该归档日期下的文章
   *
   * @param archive
   * @param request
   * @return
   */
  @GetMapping(value = "/getArchiveArticle")
  public JSONObject getArchiveArticle(String archive, HttpServletRequest request) {
    int rows = Integer.parseInt(request.getParameter("rows"));
    int pageNum = Integer.parseInt(request.getParameter("pageNum"));
    return articleService.findArticleByArchive(archive, rows, pageNum);
  }
}
