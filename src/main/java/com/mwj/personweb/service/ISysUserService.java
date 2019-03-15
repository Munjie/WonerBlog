package com.mwj.personweb.service;

import com.mwj.personweb.model.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** @Author: 母哥 @Date: 2019-03-01 16:29 @Version 1.0 */
public interface ISysUserService {

  SysUser findById(Integer id);

  SysUser findByName(String name);

  Boolean isExitUser(String name);

  JSONObject insertUser(SysUser sysUser);

  JSONObject findUserByEmail(String email);

  JSONObject resetPassword(SysUser sysUser);

  boolean updateImgUrlByName(SysUser sysUser);

  JSONArray allSysUser(String rows, String pageNum);

  JSONObject deleteUser(Integer id);

  void updateUserInfo(SysUser sysUser);

  void updateLoginTime(SysUser sysUser);

  void updateOutTime(SysUser sysUser);

  SysUser findTime(String name);

  /**
   * @description //在线时长
   * @param:
   * @return:
   * @date: 2019/3/15 16:30
   */
  void updateOnlineTime(SysUser sysUser);

  /**
   * @description //更新进度时间
   * @param:
   * @return:
   * @date: 2019/3/15 18:57
   */
  void updateLevStatus(SysUser sysUser);
  /**
   * @description //更新等级
   * @param:
   * @return:
   * @date: 2019/3/15 18:57
   */
  void updateLev(String name);
  /**
   * @description //查询进度时间
   * @param:
   * @return:
   * @date: 2019/3/15 18:57
   */
  int findLevStatus(String name);
}
