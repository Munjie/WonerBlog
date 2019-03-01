package com.mwj.personweb.service;

import com.mwj.personweb.model.SysPermission;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:15 @Version 1.0
 */
public interface ISysPermissionService {

    List<SysPermission> listByRoleId(Integer roleId);
}
