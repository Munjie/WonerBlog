package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ICommentLikeDao;
import com.mwj.personweb.exception.TipException;
import com.mwj.personweb.model.CommentLike;
import com.mwj.personweb.service.ICommentReplyService;
import com.mwj.personweb.service.ICommentService;
import com.mwj.personweb.service.ILikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Auther: munjie @Date: 2019/3/13 16:20 @Description: */
@Service
public class CommentLikeServiceImpl implements ILikeService {

  private Logger logger = LoggerFactory.getLogger(CommentLikeServiceImpl.class);

  @Autowired private ICommentLikeDao commentLikeDao;

  @Autowired private ICommentService commentService;

  @Autowired private ICommentReplyService commentReplyService;

  @Override
  public void addCommentLike(CommentLike commentLike) {

    try {
      commentService.updateAddLike(commentLike.getCoid());
      int i = commentLikeDao.addCommentLike(commentLike);
      if (i == 0) {
        throw new TipException("点赞失败");
      }
    } catch (Exception e) {
      logger.error("更新点赞表异常");
      throw new TipException("点赞失败");
    }
  }

  @Override
  public CommentLike findCommentLikeIsAuth(int coid, int authorId) {
    return commentLikeDao.findCommentLikeIsAuth(coid, authorId);
  }

  @Override
  public CommentLike findCommentLikeNotAuth(int coid, String ip, String agent) {
    return commentLikeDao.findCommentLikeNotAuth(coid, ip, agent);
  }

  @Override
  public void addReplyLike(CommentLike commentLike) {

    try {

      commentReplyService.updateReplyLike(commentLike.getCoid());
      int i = commentLikeDao.addCommentLike(commentLike);
      if (i == 0) {
        throw new TipException("点赞失败");
      }

    } catch (Exception e) {
      logger.error("更新点赞表异常");
      throw new TipException("点赞失败");
    }
  }
}
