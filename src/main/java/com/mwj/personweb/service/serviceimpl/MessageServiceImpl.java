package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.IMessageDao;
import com.mwj.personweb.model.Message;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.IMessageService;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/16 00:30 @Description: */
@Service
public class MessageServiceImpl implements IMessageService {

  private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

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
        message.setCreattime(TimeUtil.getTimeStateNew(message.getCreattime()));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return allMsg;
  }

  @Override
  public void addMessage(Message message) {
    try {

      messageDao.addMessage(message);

    } catch (Exception e) {
      logger.error("添加未读消息异常", e);
    }
  }

  @Override
  public void updateMessage(int id) {

    messageDao.updateMessage(id);
  }
}
