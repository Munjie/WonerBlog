package com.mwj.personweb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/9 19:13 @Description: */
@Mapper
@Repository
public interface ICategoryDao {

  @Select("select categoryName from categories")
  List<String> findCategoriesName();

  @Select("select count(*) from categories")
  int countCategoriesNum();
}
