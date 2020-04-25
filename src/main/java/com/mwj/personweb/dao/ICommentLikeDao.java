package com.mwj.personweb.dao;

import com.mwj.personweb.model.CommentLike;
import org.apache.ibatis.annotations.Mapper;

/** @Auther: munjie @Date: 2019/3/13 16:08 @Description: */
@Mapper
public interface ICommentLikeDao {

  int addCommentLike(CommentLike commentLike);

  CommentLike findCommentLikeIsAuth(int coid, int authorId); // 认证用户

  CommentLike findCommentLikeNotAuth(int coid, String ip, String agent); // 非认证用户
}
