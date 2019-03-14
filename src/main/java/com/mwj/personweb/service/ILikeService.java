package com.mwj.personweb.service;

import com.mwj.personweb.model.CommentLike;

/** @Auther: munjie @Date: 2019/3/13 16:16 @Description: */
public interface ILikeService {

  void addCommentLike(CommentLike commentLike);

  CommentLike findCommentLikeIsAuth(int coid, int authorId); // 认证用户

  CommentLike findCommentLikeNotAuth(int coid, String ip, String agent); // 非认证用户

  void addReplyLike(CommentLike commentLike);
}
