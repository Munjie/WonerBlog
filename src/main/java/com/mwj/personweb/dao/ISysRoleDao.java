package com.mwj.personweb.dao;

import com.mwj.personweb.model.SysRole;

import java.util.List;

/** @Author: 母哥 @Date: 2019-03-01 10:42 @Version 1.0 */
public interface ISysRoleDao {

  SysRole findById(Integer id);

  SysRole findByName(String name);

  List<SysRole> findAllSysRole();
}
