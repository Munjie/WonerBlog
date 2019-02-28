package com.mwj.personweb.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.mwj.personweb.dao.ISysLogDao;
import com.mwj.personweb.model.SysLog;
import com.mwj.personweb.service.ISysLogService;
import com.mwj.personweb.service.redis.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Author: 母哥 @Date: 2019-02-14 9:40 @Version 1.0 */
@Service
public class SysLogServiceImpl implements ISysLogService {

  @Autowired private ISysLogDao sysLogDao;

  @Autowired private RedisServer redisServer;

  @Override
  public void addLog(SysLog sysLog) {

    sysLogDao.insert(sysLog);
  }

  @Override
  public int getLogCount() {
    return 0;
  }

  @Override
  public int getViewCount() {
    return 0;
  }

  @Override
  public List<SysLog> listAllLog() {

    JSONObject json = new JSONObject();

    List<SysLog> all = sysLogDao.findAll();

    redisServer.set("syslog_list", json.toJSONString(all));

    return all;
  }
}
