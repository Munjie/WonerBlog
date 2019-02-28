package com.mwj.personweb.dao;

import com.mwj.personweb.model.SysLog;

import java.util.List;

/** @Author: 母哥 @Date: 2019-02-14 9:34 @Version 1.0 */
public interface ISysLogDao {

  int deleteByPrimaryKey(Long id);

  int insert(SysLog record);

  int insertSelective(SysLog record);

  SysLog selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(SysLog record);

  int updateByPrimaryKey(SysLog record);

  List<SysLog> findAll();
}
