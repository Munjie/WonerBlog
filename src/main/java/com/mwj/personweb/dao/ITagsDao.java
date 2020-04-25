package com.mwj.personweb.dao;

import com.mwj.personweb.model.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/9 19:34 @Description: */
@Mapper
public interface ITagsDao {

  void insertTag(Tags tag);

  int findIsExitByTagName(@Param("tagName") String tagName);

  List<Tags> findTagsCloud();

  int countTagsNum();

  int getTagsSizeByTagName(String tagName);

  List<Tags> findAllTags();

  int deleteTags(int id);

  int batchDelete(List<Integer> id);
}
