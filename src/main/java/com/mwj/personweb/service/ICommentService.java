package com.mwj.personweb.service;

import com.github.pagehelper.PageInfo;
import com.mwj.personweb.bo.CommentBo;
import com.mwj.personweb.model.CommentVo;
import com.mwj.personweb.model.CommentVoExample;

public interface ICommentService {

  /**
   * 保存对象
   *
   * @param commentVo
   */
  void insertComment(CommentVo commentVo);

  /**
   * 获取文章下的评论
   *
   * @param cid
   * @param page
   * @param limit
   * @return CommentBo
   */
  PageInfo<CommentBo> getComments(Integer cid, int page, int limit);

  /**
   * 获取文章下的评论
   *
   * @param commentVoExample
   * @param page
   * @param limit
   * @return CommentVo
   */
  PageInfo<CommentVo> getCommentsWithPage(CommentVoExample commentVoExample, int page, int limit);

  /**
   * 根据主键查询评论
   *
   * @param coid
   * @return
   */
  CommentVo getCommentById(Integer coid);

  /**
   * 删除评论，
   *
   * @param coid
   * @param cid
   * @throws Exception
   */
  void delete(Integer coid, Integer cid);

  /**
   * 更新评论状态
   *
   * @param comments
   */
  void update(CommentVo comments);

  /**
   * 删除关联文章评论，
   *
   * @param cid
   * @throws Exception
   */
  int deleteCommentsById(Integer cid);

  /**
   * @description //TODO
   * @param:
   * @return:
   * @date: 2019/3/17 9:22
   */
  void updateAddLike(int coid);
}
