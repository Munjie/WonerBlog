package com.mwj.personweb.service;

import com.mwj.personweb.model.Message;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/16 00:29 @Description: */
public interface IMessageService {

  List<Message> findAllMsg(String name);

  void addMessage(Message message);

  void updateMessage(int id);
}
