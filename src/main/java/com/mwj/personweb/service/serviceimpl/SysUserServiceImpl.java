package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.IUserDao;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.CommonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/** @Author: 母哥 @Date: 2019-03-01 10:54 @Version 1.0 */
@Service
public class SysUserServiceImpl implements ISysUserService {

  @Autowired private IUserDao userDao;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Autowired private RedisServer redisServer;

  public SysUser findById(Integer id) {
    return userDao.findById(id);
  }

  public SysUser findByName(String name) {
    return userDao.findByName(name);
  }

  @Override
  public Boolean isExitUser(String name) {

    SysUser user = userDao.findByName(name);
    return user != null;
  }

  @Override
  public JSONObject insertUser(SysUser sysUser) {
    JSONObject jsonObject = new JSONObject();
    if (sysUser != null) {
      sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));

      sysUser.setImgUrl(CommonUtil.gravatarImg(sysUser.getEmail()));
      int i = userDao.insertSysUser(sysUser);

      if (i > 0) {

        jsonObject.put("status", "200");
      } else {

        jsonObject.put("status", "400");
      }
    } else {

      jsonObject.put("status", "400");
    }

    return jsonObject;
  }

  @Override
  public JSONObject findUserByEmail(String email) {

    JSONObject jsonObject = new JSONObject();
    if (StringUtils.isNotBlank(email)) {

      List<SysUser> userList = userDao.findUserByEmail(email);
      if (CollectionUtils.isEmpty(userList)) {
        jsonObject.put("status", "400");
        jsonObject.put("msg", "该邮箱所属用户不存在");
      } else {

        jsonObject.put("status", "200");
      }
    }
    return jsonObject;
  }

  @Override
  public JSONObject resetPassword(SysUser sysUser) {
    JSONObject jsonObject = new JSONObject();
    try {
      if (sysUser != null) {

        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        int i = userDao.resetPassword(sysUser);
        if (i > 0) {
          jsonObject.put("status", "200");
          jsonObject.put("msg", "重置密码成功，密码已发送邮箱");
        } else {
          jsonObject.put("status", "400");
          jsonObject.put("msg", "重置密码失败");
        }
      }
    } catch (Exception e) {
      jsonObject.put("status", "400");
      jsonObject.put("msg", "重置密码失败");
    }

    return jsonObject;
  }

  @Override
  public boolean updateImgUrlByName(SysUser sysUser) {
    boolean falg = true;
    try {
      if (sysUser != null) {
        int i = userDao.updateImgUrlByName(sysUser);
        if (i > 0) {
          if (redisServer.hasKey(sysUser.getName())) {
            if (redisServer.delKey(sysUser.getName())) {
              falg = true;
            }
          }
        } else {
          falg = false;
        }
      }

    } catch (Exception e) {
      falg = false;
    }
    return falg;
  }
}
