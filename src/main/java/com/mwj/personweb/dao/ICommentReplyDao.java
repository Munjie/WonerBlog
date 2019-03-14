package com.mwj.personweb.dao;

import com.mwj.personweb.model.CommentReply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/14 00:03 @Description: */
@Mapper
@Repository
public interface ICommentReplyDao {

  @Select("select * from t_reply where coid=#{coid} order by id desc")
  List<CommentReply> findCommentReplyByCoid(int coid);

  @Insert(
      " insert into t_reply (id, coid, authorId,\n"
          + "      replyId, comment, authorImg,replyName,authorName,created)\n"
          + "    values (#{id,jdbcType=INTEGER}, #{coid,jdbcType=INTEGER}, #{authorId,jdbcType=INTEGER},\n"
          + "      #{replyId,jdbcType=INTEGER}, #{comment,jdbcType=VARCHAR}, #{authorImg,jdbcType=VARCHAR},#{replyName,jdbcType=VARCHAR},#{authorName,jdbcType=VARCHAR},#{created,jdbcType=VARCHAR})")
  int addReplyCommemt(CommentReply commentReply);

  @Update(
      "     update t_reply\n"
          + "    set likes = likes +1\n"
          + "    where id = #{id,jdbcType=INTEGER}")
  int updateReplyLike(int id);
}
