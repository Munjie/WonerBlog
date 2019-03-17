package com.mwj.personweb.service;

import com.mwj.personweb.model.CommentReply;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/14 00:09 @Description: */
public interface ICommentReplyService {

  List<CommentReply> findCommentReplyByCoid(int coid);

  void addReplyComment(CommentReply commentReply);

  void updateReplyLike(int id);

  /**
   * @description //根据id删除回复
   * @param:
   * @return:
   * @date: 2019/3/17 9:19
   */
  void deleteReply(int coid);
  /**
   * @description //根据文章id删除回复
   * @param:
   * @return:
   * @date: 2019/3/17 9:19
   */
  void deleteReplyByCid(int coid);
}
