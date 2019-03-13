package com.mwj.personweb.service;

import com.mwj.personweb.model.CommentLike;

/** @Auther: munjie @Date: 2019/3/13 16:16 @Description: */
public interface ICommentLikeService {

  void addCommentLike(CommentLike commentLike);

  int upDateLike(int id);

  CommentLike findCommentLikeIsAuth(int coid, int authorId); // 认证用户

  CommentLike findCommentLikeNotAuth(int coid, String ip, String agent); // 非认证用户
}
