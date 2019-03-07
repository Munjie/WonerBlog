package com.mwj.personweb.service;

import com.mwj.personweb.model.SysUser;
import net.sf.json.JSONObject;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:29 @Version 1.0
 */
public interface ISysUserService {

    SysUser findById(Integer id);

    SysUser findByName(String name);

    Boolean isExitUser(String name);

    JSONObject insertUser(SysUser sysUser);

    JSONObject findUserByEmail(String email);

    JSONObject resetPassword(SysUser sysUser);
}
