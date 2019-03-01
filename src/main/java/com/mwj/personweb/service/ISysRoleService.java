package com.mwj.personweb.service;

import com.mwj.personweb.model.SysRole;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:17 @Version 1.0
 */
public interface ISysRoleService {

    SysRole selectById(Integer id);

    SysRole selectByName(String name);
}
