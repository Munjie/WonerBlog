package com.mwj.personweb.dao;

import com.mwj.personweb.model.CommentLike;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/** @Auther: munjie @Date: 2019/3/13 16:08 @Description: */
@Mapper
@Repository
public interface ICommentLikeDao {
  @Insert(
      " insert into comment_likes (id, cid, coid,\n"
          + "      authorId, ip, agent)\n"
          + "    values (#{id,jdbcType=INTEGER}, #{cid,jdbcType=INTEGER}, #{coid,jdbcType=INTEGER},\n"
          + "      #{authorId,jdbcType=INTEGER}, #{ip,jdbcType=VARCHAR}, #{agent,jdbcType=VARCHAR})")
  int addCommentLike(CommentLike commentLike);

  int upDateLike(int id);

  @Select("select   *     from  comment_likes  where    coid=#{coid} and authorId=#{authorId}")
  CommentLike findCommentLikeIsAuth(int coid, int authorId); // 认证用户

  @Select("select  * from  comment_likes  where  coid=#{coid} and ip=#{ip} and  agent=#{agent}")
  CommentLike findCommentLikeNotAuth(int coid, String ip, String agent); // 非认证用户
}
