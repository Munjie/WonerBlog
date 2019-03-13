package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ISysRoleDao;
import com.mwj.personweb.model.SysRole;
import com.mwj.personweb.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Author: 母哥 @Date: 2019-03-01 10:56 @Version 1.0 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

  @Autowired private ISysRoleDao sysRoleDao;

  public SysRole selectById(Integer id) {
    return sysRoleDao.findById(id);
  }

  @Override
  public List<SysRole> findAllSysRole() {
    return sysRoleDao.findAllSysRole();
  }

  @Override
  public SysRole selectByName(String name) {
    return sysRoleDao.findByName(name);
  }
}
