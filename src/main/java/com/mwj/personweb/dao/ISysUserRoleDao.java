package com.mwj.personweb.dao;

import com.mwj.personweb.model.SysUserRole;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 10:45 @Version 1.0
 */
public interface ISysUserRoleDao {

    List<SysUserRole> findByUserId(Integer userId);
}
