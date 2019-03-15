package com.mwj.personweb.service.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mwj.personweb.dao.IUserDao;
import com.mwj.personweb.exception.TipException;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.CommonUtil;
import com.mwj.personweb.utils.ReturnMsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @Author: 母哥 @Date: 2019-03-01 10:54 @Version 1.0 */
@Service
public class SysUserServiceImpl implements ISysUserService {

  private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

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

  @Override
  public JSONArray allSysUser(String rows, String pageNum) {

    int pageNo = Integer.parseInt(pageNum);
    int pageSize = Integer.parseInt(rows);

    PageHelper.startPage(pageNo, pageSize);
    List<SysUser> sysUsers = userDao.allSysUser();
    PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
    List<Map<String, Object>> newUser = new ArrayList<>();
    Map<String, Object> map;

    for (SysUser sysUser : sysUsers) {
      map = new HashMap<>();
      map.put("id", sysUser.getId());
      map.put("name", sysUser.getName());
      map.put("email", sysUser.getEmail());
      map.put("imgUrl", sysUser.getImgUrl());
      newUser.add(map);
    }
    JSONArray jsonArray = JSONArray.fromObject(newUser);
    Map<String, Object> thisPageInfo = new HashMap<>();
    thisPageInfo.put("pageNum", pageInfo.getPageNum());
    thisPageInfo.put("pageSize", pageInfo.getPageSize());
    thisPageInfo.put("total", pageInfo.getTotal());
    thisPageInfo.put("pages", pageInfo.getPages());
    thisPageInfo.put("isFirstPage", pageInfo.isIsFirstPage());
    thisPageInfo.put("isLastPage", pageInfo.isIsLastPage());

    jsonArray.add(thisPageInfo);
    //    logger.info(jsonArray.toString());
    return jsonArray;
  }

  @Override
  public void updateLoginTime(SysUser sysUser) {
    userDao.updateLoginTime(sysUser);
  }

  @Override
  public void updateOnlineTime(SysUser sysUser) {
    userDao.updateOnlineTime(sysUser);
  }

  @Override
  public SysUser findTime(String name) {

    return userDao.findTime(name);
  }

  @Override
  public void updateOutTime(SysUser sysUser) {

    userDao.updateOutTime(sysUser);
  }

  @Override
  public void updateUserInfo(SysUser sysUser) {
    try {
      int i = userDao.updateUserInfo(sysUser);
      if (redisServer.hasKey(sysUser.getName())) {

        redisServer.remove(sysUser.getName());
      }
      if (i == 0) {
        throw new TipException("保存失败");
      }
    } catch (Exception e) {
      logger.error(sysUser.getId() + "更新用户信息异常", e);
      throw new TipException("更新失败");
    }
  }

  @Override
  public JSONObject deleteUser(Integer id) {

    try {
      int i = userDao.deleteUser(id);
      if (i > 0) {
        return ReturnMsgUtil.bulidRetunMsg("200", "删除用户成功");
      } else {

        return ReturnMsgUtil.bulidRetunMsg("400", "删除用失败");
      }
    } catch (Exception e) {
      logger.error("删除" + id + "失败");
      return ReturnMsgUtil.bulidRetunMsg("400", "删除用失败");
    }
  }

  @Override
  public void updateLevStatus(SysUser sysUser) {

    userDao.updateLevStatus(sysUser);
    redisServer.remove(sysUser.getName());
  }

  @Override
  public void updateLev(SysUser sysUser) {

    userDao.updateLev(sysUser);
    redisServer.remove(sysUser.getName());
  }

  @Override
  public int findLevStatus(String name) {
    return userDao.findLevStatus(name);
  }

  @Override
  public void updateLevStatusNotAdd(SysUser sysUser) {
    userDao.updateLevStatusNotAdd(sysUser);
    redisServer.remove(sysUser.getName());
  }
}
