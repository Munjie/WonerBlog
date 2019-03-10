package com.mwj.personweb.handler;

import com.mwj.personweb.constant.WebConst;
import com.mwj.personweb.tdo.Types;
import com.mwj.personweb.utils.Commons;
import com.mwj.personweb.utils.IPUtil;
import com.mwj.personweb.utils.MapCache;
import com.mwj.personweb.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/** @Auther: munjie @Date: 2019/3/9 16:14 @Description: */
@Component
public class InterceptorHandler implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(InterceptorHandler.class);

  private static final String USER_AGENT = "user-agent";

  @Autowired private Commons commons;

  private MapCache cache = MapCache.single();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    Principal userPrincipal = request.getUserPrincipal();
    String user = "匿名用户";
    if (userPrincipal != null) {
      user = userPrincipal.getName();
    }

    String uri = request.getRequestURI();
    String ip = IPUtil.getIpAddrByRequest(request);
    logger.info("UserAgent: {}", request.getHeader(USER_AGENT));
    logger.info(user + "访问博客地址: {}, 来路地址: {}", uri, ip);

    // 设置get请求的token
    if (request.getMethod().equals("GET") || request.getMethod().equals("POST")) {
      String csrf_token = UUID.UU64();
      // 默认存储30分钟
      cache.hset(Types.CSRF_TOKEN.getType(), csrf_token, uri, 30 * 60);
      request.setAttribute("_csrf_token", csrf_token);
    }
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    String ip = IPUtil.getIpAddrByRequest(request);
    // 禁止该ip访问
    if (WebConst.BLOCK_IPS.contains(ip)) {
      // 强制跳转
      modelAndView.setViewName("comm/ipban");
    }

    request.setAttribute("commons", commons);
  }
}
