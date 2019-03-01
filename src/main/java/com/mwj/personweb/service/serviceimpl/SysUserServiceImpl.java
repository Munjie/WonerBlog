package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.IUserDao;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 母哥 @Date: 2019-03-01 10:54 @Version 1.0
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private IUserDao userDao;

    public SysUser findById(Integer id) {
        return userDao.findById(id);
    }

    public SysUser findByName(String name) {
        return userDao.findByName(name);
    }
}
