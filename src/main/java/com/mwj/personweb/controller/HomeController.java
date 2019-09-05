package com.mwj.personweb.controller;

import com.mwj.personweb.model.Tags;
import com.mwj.personweb.service.ITagsService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.JsonUtil;
import com.mwj.personweb.utils.PageUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;

/** @Author: 母哥 @Date: 2019-03-04 11:28 @Version 1.0 */
@Controller
public class HomeController {
  private static Logger logger = LoggerFactory.getLogger(HomeController.class);

  private static final String USER_AGENT = "user-agent";

  @Autowired private PageUtil pageUtil;

  @Autowired private ITagsService tagService;

  @Autowired private RedisServer redisServer;

  @GetMapping("/")
  public String index(Authentication authentication, Model model) throws Exception {
    return pageUtil.forward(authentication, model, "front/index");
  }

  @GetMapping("/login.html")
  public String login() {
    return "front/login";
  }

  @GetMapping("/loveYou")
  public String loveYou() {
    return "front/love_you";
  }

  @GetMapping("/register.html")
  public String register() {
    return "front/register";
  }

  @PostMapping(value = "/check_edit")
  @ResponseBody
  public JSONObject toEdit(HttpServletRequest request, String name) throws Exception {
    JSONObject object = new JSONObject();
    Principal principal = request.getUserPrincipal();
    if (principal == null) {
      object.put("status", "400");
    }

    return object;
  }

  @GetMapping("/article_edit.html")
  public String articleEdit(Authentication authentication, Model model) throws Exception {
    return pageUtil.forward(authentication, model, "front/edit_article");
  }

  @GetMapping("/archives.html")
  public String archives(
      HttpServletResponse response,
      HttpServletRequest request,
      Authentication authentication,
      Model model)
      throws Exception {
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    request.getSession().removeAttribute("lastUrl");
    String archive = request.getParameter("archive");

    try {
      response.setHeader("archive", archive);
    } catch (Exception e) {
    }
    return pageUtil.forward(authentication, model, "front/archives_article");
  }

  @GetMapping("/tags.html")
  public String tags(
      Authentication authentication,
      Model model,
      HttpServletResponse response,
      HttpServletRequest request)
      throws Exception {
    List<Tags> tags = null;
    if (redisServer.hasKey("tags")) {
      String tags_temp = redisServer.get("tags");
      tags = JsonUtil.getStringToList(tags_temp, Tags.class);
      logger.info("get tags from redis success!");

    } else {
      tags = tagService.allTags();
      String listToJson = JsonUtil.getListToJson(tags);
      redisServer.set("tags", listToJson);
      logger.info("set tags to redis success!");
    }
    model.addAttribute("tags", tags);
    return pageUtil.forward(authentication, model, "front/tags");
  }

  @RequestMapping("/mp3")
  public String getAudio(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String range = request.getHeader("Range");
    String[] rs = range.split("\\=");
    range = rs[1].split("\\-")[0];

    String path = request.getServletContext().getRealPath("/");
    File file = new File("/appdata/web/2019.mp3");
    if (!file.exists()) throw new RuntimeException("音频文件不存在 --> 404");
    OutputStream os = response.getOutputStream();
    FileInputStream fis = new FileInputStream(file);
    long length = file.length();
    System.out.println("file length : " + length);
    // 播放进度
    int count = 0;
    // 播放百分比
    int percent = (int) (length * 0.4);

    int irange = Integer.parseInt(range);
    length = length - irange;

    response.addHeader("Accept-Ranges", "bytes");
    response.addHeader("Content-Length", length + "");
    response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
    response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");

    int len = 0;
    byte[] b = new byte[1024];
    while ((len = fis.read(b)) != -1) {
      os.write(b, 0, len);
      count += len;
      if (count >= percent) {
        break;
      }
    }
    System.out.println("count: " + count + ", percent: " + percent);
    fis.close();
    os.close();
    return null;
  }
}
