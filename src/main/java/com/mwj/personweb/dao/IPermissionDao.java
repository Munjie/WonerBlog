package com.mwj.personweb.dao;

import com.mwj.personweb.model.Permission;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-02-28 14:22 @Version 1.0
 */
public interface IPermissionDao {

    public List<Permission> findAll();

    public List<Permission> findByAdminUserId(int userId);
}
