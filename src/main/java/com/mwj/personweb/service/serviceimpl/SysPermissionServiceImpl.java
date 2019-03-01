package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ISysPermissionDao;
import com.mwj.personweb.model.SysPermission;
import com.mwj.personweb.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:08 @Version 1.0
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Autowired
    private ISysPermissionDao sysPermissionDao;

    /**
     * 获取指定角色所有权限
     */
    public List<SysPermission> listByRoleId(Integer roleId) {
        return sysPermissionDao.findListByRoleId(roleId);
    }
}
