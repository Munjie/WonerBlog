package com.mwj.personweb.dao;

import com.mwj.personweb.model.SysUser;

/**
 * @Author: 母哥 @Date: 2019-02-28 14:53 @Version 1.0
 */
public interface IUserDao {

    SysUser findById(Integer id);

    SysUser findByName(String name);
}
