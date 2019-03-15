package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.IMessageDao;
import com.mwj.personweb.model.Message;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.IMessageService;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/16 00:30 @Description: */
@Service
public class MessageServiceImpl implements IMessageService {

  @Autowired private RedisServer redisServer;

  @Autowired private ISysUserService sysUserService;

  @Autowired private IMessageDao messageDao;

  @Override
  public List<Message> findAllMsg(String name) {
    List<Message> allMsg = null;
    try {

      SysUser byName = sysUserService.findByName(name);
      allMsg = messageDao.findAllMsg(name);
      for (Message message : allMsg) {
        message.setCreate(TimeUtil.getTimeStateNew(message.getCreate()));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return allMsg;
  }

  @Override
  public void addMessage(Message message) {}

  @Override
  public void updateMessage(int id) {}
}
