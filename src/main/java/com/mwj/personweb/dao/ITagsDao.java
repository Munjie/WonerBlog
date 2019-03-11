package com.mwj.personweb.dao;

import com.mwj.personweb.model.Tags;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/9 19:34 @Description: */
@Mapper
@Repository
public interface ITagsDao {

  @Insert("insert into tags(tagName,tagSize) values(#{tagName},#{tagSize})")
  void insertTag(Tags tag);

  @Select("select IFNULL(max(id),0) from tags where tagName=#{tagName}")
  int findIsExitByTagName(@Param("tagName") String tagName);

  @Select("select * from tags order by id desc")
  List<Tags> findTagsCloud();

  @Select("select count(*) from tags")
  int countTagsNum();

  @Select("select tagSize from tags where tagName=#{tagName}")
  int getTagsSizeByTagName(String tagName);

  @Select("select * from tags order by id asc")
  List<Tags> findAllTags();

  @Delete("delete from tags where id=#{id}")
  int deleteTags(int id);

  @Delete(
      "<script>  delete from tags where id in\n"
          + "        <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n"
          + "            #{item}\n"
          + "        </foreach></script>")
  int batchDelete(List<Integer> id);
}
