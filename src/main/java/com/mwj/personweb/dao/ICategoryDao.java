package com.mwj.personweb.dao;

import com.mwj.personweb.model.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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

  @Select("select * from categories order by id asc")
  List<Category> findAllCategory();

  @Delete("delete from categories where id=#{id}")
  int deleteCategory(int id);

  @Insert(" insert into categories (id, categoryName) values (#{id}, #{categoryName})")
  int addCategories(Category category);
}
