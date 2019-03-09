package com.mwj.personweb.controller;

import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.utils.FileUtil;
import com.mwj.personweb.utils.PageUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/** @Author: 母哥 @Date: 2019-03-08 10:02 @Version 1.0 */
@Controller
public class UserInfoController {

  private static Logger logger = LoggerFactory.getLogger(BackController.class);

  @Autowired private PageUtil pageUtil;

  @Autowired private ISysUserService sysUserService;

  @GetMapping(value = "/userInfo")
  public String getUserInfo(Authentication authentication, Model model) throws Exception {

    return pageUtil.forward(authentication, model, "front/user_info");
  }

  @PostMapping(value = "/uploadUserImg")
  @ResponseBody
  public JSONObject uploadUserImg(
      Authentication authentication, @RequestParam("user-pic") MultipartFile file)
      throws Exception {
    JSONObject object = new JSONObject();
    SysUser sysUser = new SysUser();

    String fileUrl = FileUtil.upload(file);
    if (StringUtils.isNotBlank(fileUrl)) {
      sysUser.setImgUrl(fileUrl);
      sysUser.setName(authentication.getName());
      boolean b = sysUserService.updateImgUrlByName(sysUser);

      if (b) {

        object.put("status", "200");
        object.put("imgUrl", fileUrl);
      } else {

        object.put("status", "400");
      }
    } else {

      object.put("status", "400");
    }

    return object;
  }
}
