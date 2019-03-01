package com.mwj.personweb.service;

import com.mwj.personweb.model.SysUserRole;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:18 @Version 1.0
 */
public interface ISysUserRoleService {

    List<SysUserRole> listByUserId(Integer userId);
}
