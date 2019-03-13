package com.mwj.personweb.dao;

import com.mwj.personweb.model.CommentReply;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/14 00:03 @Description: */
@Mapper
@Repository
public interface ICommentReplyDao {

  @Select("select * from t_reply where coid=#{coid}")
  List<CommentReply> findCommentReplyByCoid(int coid);
}
