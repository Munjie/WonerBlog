package com.mwj.personweb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/** @Auther: munjie @Date: 2019/3/17 10:28 @Description: */
@Mapper
@Repository
public interface IVisitorDao {

  @Update("update visitor set totle = totle+1 where detail='history'")
  int updateHistory();

  @Update("update visitor set totle = totle+1 where detail= 'today'")
  int updateToday();

  @Update("update visitor set totle = totle+1 where detail= 'other'")
  int updateOther();

  @Select("select totle from visitor where detail='history' ")
  long getHistory();

  @Select("select totle from visitor where detail='today' ")
  long getToday();

  @Select("select totle from visitor where detail='other' ")
  long getOther();
}
