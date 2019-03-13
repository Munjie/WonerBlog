package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ICommentReplyDao;
import com.mwj.personweb.model.CommentReply;
import com.mwj.personweb.service.ICommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/14 00:09 @Description: */
@Service
public class CommentReplyServiceImpl implements ICommentReplyService {
  @Autowired private ICommentReplyDao commentReplyDao;

  @Override
  public List<CommentReply> findCommentReplyByCoid(int coid) {
    return commentReplyDao.findCommentReplyByCoid(coid);
  }
}
