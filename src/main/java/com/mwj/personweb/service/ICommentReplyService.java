package com.mwj.personweb.service;

import com.mwj.personweb.model.CommentReply;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/14 00:09 @Description: */
public interface ICommentReplyService {

  List<CommentReply> findCommentReplyByCoid(int coid);
}
