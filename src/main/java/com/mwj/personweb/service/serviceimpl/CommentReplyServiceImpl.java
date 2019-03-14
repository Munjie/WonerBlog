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
}
