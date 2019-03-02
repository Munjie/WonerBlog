package com.mwj.personweb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @Author: 母哥 @Date: 2019-03-01 17:02 @Version 1.0 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    logger.info("登录成功,{}", authentication);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(authentication));
    response.sendRedirect("/admin/login/success");
  }
}
