package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ICommentLikeDao;
import com.mwj.personweb.exception.TipException;
import com.mwj.personweb.model.CommentLike;
import com.mwj.personweb.service.ICommentLikeService;
import com.mwj.personweb.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Auther: munjie @Date: 2019/3/13 16:20 @Description: */
@Service
public class CommentLikeServiceImpl implements ICommentLikeService {

  @Autowired private ICommentLikeDao commentLikeDao;

  @Autowired private ICommentService commentService;

  @Override
  public void addCommentLike(CommentLike commentLike) {

    commentService.updateAddLike(commentLike.getCoid());
    int i = commentLikeDao.addCommentLike(commentLike);
    if (i == 0) {
      throw new TipException("点赞失败");
    }
  }

  @Override
  public int upDateLike(int id) {
    return 0;
  }

  @Override
  public CommentLike findCommentLikeIsAuth(int coid, int authorId) {
    return commentLikeDao.findCommentLikeIsAuth(coid, authorId);
  }

  @Override
  public CommentLike findCommentLikeNotAuth(int coid, String ip, String agent) {
    return commentLikeDao.findCommentLikeNotAuth(coid, ip, agent);
  }
}
