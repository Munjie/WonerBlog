package com.mwj.personweb.dao;

import com.mwj.personweb.model.SysUser;

import java.util.List;

/** @Author: 母哥 @Date: 2019-02-28 14:53 @Version 1.0 */
public interface IUserDao {

  SysUser findById(Integer id);

  SysUser findByName(String name);

  int insertSysUser(SysUser sysUser);

  List<SysUser> findUserByEmail(String email);

  int resetPassword(SysUser sysUser);

  int updateImgUrlByName(SysUser sysUser);

  List<SysUser> allSysUser();

  int deleteUser(Integer id);

  int updateUserInfo(SysUser sysUser);

  int updateLoginTime(SysUser sysUser);

  int updateOutTime(SysUser sysUser);

  SysUser findTime(String name);

  int updateOnlineTime(SysUser sysUser);
  /**
   * @description //更新进度时间
   * @param:
   * @return:
   * @date: 2019/3/15 18:57
   */
  int updateLevStatus(SysUser sysUser);

  /**
   * @description //更新进度时间满减
   * @param:
   * @return:
   * @date: 2019/3/15 18:57
   */
  int updateLevStatusNotAdd(SysUser sysUser);

  /**
   * @description //更新等级
   * @param:
   * @return:
   * @date: 2019/3/15 18:57
   */
  int updateLev(SysUser sysUser);
  /**
   * @description //查询进度时间
   * @param:
   * @return:
   * @date: 2019/3/15 18:57
   */
  int findLevStatus(String name);
}
