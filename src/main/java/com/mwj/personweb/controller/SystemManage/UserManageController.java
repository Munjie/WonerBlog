package com.mwj.personweb.controller.SystemManage;

import com.mwj.personweb.service.ISysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** @Auther: munjie @Date: 2019/3/12 18:59 @Description: */
@Controller
public class UserManageController {

  @Autowired private ISysUserService sysUserService;

  /**
   * @param rows 一页的大小
   * @param pageNum 当前页
   */
  @PostMapping(value = "/all_UserInfo")
  @ResponseBody
  public JSONArray allUserInfo(String rows, String pageNum) {

    return sysUserService.allSysUser(rows, pageNum);
  }
  /**
   * @description //根据id删除用户
   * @param:
   * @return:
   * @date: 2019/3/12 19:33
   */
  @PostMapping(value = "/delete_User")
  @ResponseBody
  public JSONObject deleteUser(int id) {

    return sysUserService.deleteUser(id);
  }
}
