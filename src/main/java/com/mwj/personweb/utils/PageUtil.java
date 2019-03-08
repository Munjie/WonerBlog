package com.mwj.personweb.utils;

import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.service.redis.RedisServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @Author: 母哥 @Date: 2019-03-08 11:17 @Version 1.0
 */
@Component
public class PageUtil {

    private static Logger logger = LoggerFactory.getLogger(PageUtil.class);

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RedisServer redisServer;

    public String forward(Authentication authentication, Model model, String page) throws Exception {
        SysUser user = null;
        if (authentication != null && authentication.getName() != null) {
            if (redisServer.hasKey(authentication.getName())) {
                String redis = redisServer.get(authentication.getName());
                user = JsonUtil.getStringToObject(redis, SysUser.class);
            } else {
                user = sysUserService.findByName(authentication.getName());
                redisServer.set(user.getName(), JsonUtil.getObjectToJson(user));
            }
            String s = CommonUtil.gravatarImg(user.getEmail());
            model.addAttribute("userImg", user.getImgUrl());
            model.addAttribute("email", user.getEmail());
        }

        return page;
  }
}
