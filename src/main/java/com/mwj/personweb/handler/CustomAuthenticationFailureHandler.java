package com.mwj.personweb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwj.personweb.exception.MyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @Author: 母哥 @Date: 2019-03-01 17:03 @Version 1.0 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
  @Autowired private ObjectMapper objectMapper;

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    logger.info("登陆失败");

    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType("application/json;charset=UTF-8");
    //    response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
    //    // response.sendRedirect(
    //    //    "/login?error=" + objectMapper.writeValueAsString(exception.getMessage()));
    //    response.sendRedirect("/admin/login/error");
    request.getSession().setAttribute("SECURITY_EXCEPTION", new MyRuntimeException("用户名或密码错误"));
    // 转发到错误Url
    request.getRequestDispatcher("/admin/login/error").forward(request, response);
    return;
  }
}
