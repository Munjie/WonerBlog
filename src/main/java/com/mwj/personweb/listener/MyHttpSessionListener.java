package com.mwj.personweb.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/** @Auther: munjie @Date: 2019/3/11 08:18 @Description: */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

  private Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

  public int count = 0; // 记录session的数量
  // 监听session的创建,synchronized 防并发bug
  public synchronized void sessionCreated(HttpSessionEvent arg0) {
    logger.info("【HttpSessionListener监听器】count++ 增加");
    count++;
    arg0.getSession().getServletContext().setAttribute("count", count);
  }

  @Override
  public synchronized void sessionDestroyed(HttpSessionEvent arg0) { // 监听session的撤销
    logger.info("【HttpSessionListener监听器】count-- 减少");
    count--;
    arg0.getSession().getServletContext().setAttribute("count", count);
  }
}
