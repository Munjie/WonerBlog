package com.mwj.personweb.dao;

import com.mwj.personweb.model.Message;
import org.mapstruct.Mapper;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/16 00:15 @Description: */
@Mapper
public interface IMessageDao {

  List<Message> findAllMsg(String sysuser);

  int addMessage(Message message);

  int updateMessage(int id);
}
