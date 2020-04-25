package com.mwj.personweb.dao;

import org.apache.ibatis.annotations.Mapper;

/** @Auther: munjie @Date: 2019/3/17 10:28 @Description: */
@Mapper
public interface IVisitorDao {

  int updateHistory();

  int updateToday();

  int updateOther();

  int updatePc();

  int updateMobile();

  int updateTodayZero();

  int getHistory();

  int getToday();

  int getOther();

  int getPc();

  int getMobile();
}
