package com.mwj.personweb.handler;

import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/** @Author: 母哥 @Date: 2019-03-01 17:13 @Version 1.0 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

  Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private ISysUserService sysUserService;

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    if (authentication != null) {
      String username = ((User) authentication.getPrincipal()).getUsername();
      SysUser sysUser = new SysUser();

      SysUser time = sysUserService.findTime(username);
      long l = TimeUtil.calcLoginTime(time.getLogintime(), new Date());
      if (l > 0) {
        sysUser.setName(username);
        sysUser.setOnlinetimes(l);
        sysUserService.updateOnlineTime(sysUser);
      }
      log.info("退出成功，当前用户名：{}", username);
      sysUser.setOuttime(new Date());
      sysUserService.updateOutTime(sysUser);

      // 重定向到登录页
      response.sendRedirect("/");
    }
  }
}
