package com.mwj.personweb.dao;

import com.mwj.personweb.model.SysPermission;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:01 @Version 1.0
 */
public interface ISysPermissionDao {

    List<SysPermission> findListByRoleId(Integer roleId);
}
