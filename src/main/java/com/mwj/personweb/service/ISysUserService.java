package com.mwj.personweb.service;

import com.mwj.personweb.model.SysUser;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:29 @Version 1.0
 */
public interface ISysUserService {

    SysUser findById(Integer id);

    SysUser findByName(String name);
}
