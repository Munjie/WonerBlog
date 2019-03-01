package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ISysUserRoleDao;
import com.mwj.personweb.model.SysUserRole;
import com.mwj.personweb.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 10:59 @Version 1.0
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

    @Autowired
    private ISysUserRoleDao sysUserRoleDao;

    public List<SysUserRole> listByUserId(Integer userId) {
        return sysUserRoleDao.findByUserId(userId);
    }
}
