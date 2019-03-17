package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ICommentReplyDao;
import com.mwj.personweb.exception.TipException;
import com.mwj.personweb.model.CommentReply;
import com.mwj.personweb.service.ICommentReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/14 00:09 @Description: */
@Service
public class CommentReplyServiceImpl implements ICommentReplyService {

  private Logger logger = LoggerFactory.getLogger(CommentReplyServiceImpl.class);

  @Autowired private ICommentReplyDao commentReplyDao;

  @Override
  public void addReplyComment(CommentReply commentReply) {
    try {
      int i = commentReplyDao.addReplyCommemt(commentReply);
      if (i == 0) {
        throw new TipException("回复" + commentReply.getReplyName() + "失败");
      }

    } catch (Exception e) {
      logger.error("插入回复表异常" + commentReply.getCoid(), e);
      throw new TipException("回复" + commentReply.getReplyName() + "失败");
    }
  }

  @Override
  public List<CommentReply> findCommentReplyByCoid(int coid) {
    return commentReplyDao.findCommentReplyByCoid(coid);
  }

  @Override
  public void deleteReply(int coid) {

    try {
      int i = commentReplyDao.deleteReply(coid);
      if (i == 0) {
        throw new TipException("点赞失败");
      }

    } catch (Exception e) {
      logger.error(coid + "删除回复", e);
      throw new TipException("删除失败");
    }
  }

  @Override
  public void updateReplyLike(int id) {

    try {
      int i = commentReplyDao.updateReplyLike(id);
      if (i == 0) {
        throw new TipException("点赞失败");
      }

    } catch (Exception e) {
      logger.error(id + "更新点赞表异常", e);
      throw new TipException("点赞失败");
    }
  }

  @Override
  public void deleteReplyByCid(int cid) {

    try {
      int i = commentReplyDao.deleteReplyByCid(cid);
      if (i == 0) {
        throw new TipException("删除回复失败");
      }

    } catch (Exception e) {
      logger.error(cid + "删除回复失败", e);
      throw new TipException("删除回复失败");
    }
  }
}
