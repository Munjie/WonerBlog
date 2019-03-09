package com.mwj.personweb.handler;

import com.mwj.personweb.utils.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/** @Auther: munjie @Date: 2019/3/9 16:14 @Description: */
@Component
public class InterceptorHandler implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(InterceptorHandler.class);
  private static final String USER_AGENT = "user-agent";

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
    return true;
  }
}
