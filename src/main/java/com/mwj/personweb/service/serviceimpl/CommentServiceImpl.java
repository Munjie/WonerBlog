package com.mwj.personweb.service.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mwj.personweb.bo.CommentBo;
import com.mwj.personweb.dao.ICommentVoDao;
import com.mwj.personweb.exception.TipException;
import com.mwj.personweb.model.Article;
import com.mwj.personweb.model.CommentReply;
import com.mwj.personweb.model.CommentVo;
import com.mwj.personweb.model.CommentVoExample;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.service.ICommentReplyService;
import com.mwj.personweb.service.ICommentService;
import com.mwj.personweb.service.redis.RedisServer;
import com.mwj.personweb.utils.DateKit;
import com.mwj.personweb.utils.MyUtils;
import com.mwj.personweb.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

  private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

  @Autowired private ICommentVoDao commentDao;

  @Autowired private IArticleService articleService;

  @Autowired private ICommentReplyService commentReplyService;

  @Autowired private RedisServer redisServer;

  @Override
  public void insertComment(CommentVo comments) {
    // 检查评论输入数据
    checkComment(comments);
    // ContentVo contents = contentService.getContents(String.valueOf(comments.getCid()));
    Article article = articleService.getArticleByMainId(comments.getCid());
    if (null == article) {
      throw new TipException("不存在的文章");
    }
    comments.setOwnerId(article.getAuthorId());
    int currentUnixTime = DateKit.getCurrentUnixTime();
    comments.setCreated(TimeUtil.fmtdate(currentUnixTime, "yyyy-MM-dd HH:mm:ss"));
    comments.setCreated(String.valueOf(System.currentTimeMillis()));
    commentDao.insertSelective(comments);
    if (article.getCommentsNum() == null) {
      article.setCommentsNum(1);
    } else {
      article.setCommentsNum(article.getCommentsNum() + 1);
    }
    articleService.updateArticleById(article);
  }

  @Override
  public PageInfo<CommentBo> getComments(Integer cid, int page, int limit) {
    if (null != cid) {
      PageHelper.startPage(page, limit);
      CommentVoExample commentVoExample = new CommentVoExample();
      commentVoExample.createCriteria().andCidEqualTo(cid).andParentEqualTo(0);
      commentVoExample.setOrderByClause("coid desc");
      List<CommentVo> parents = commentDao.selectByExampleWithBLOBs(commentVoExample);
      PageInfo<CommentVo> commentPaginator = new PageInfo<>(parents);
      PageInfo<CommentBo> returnBo = copyPageInfo(commentPaginator);
      if (parents.size() != 0) {
        List<CommentBo> comments = new ArrayList<>(parents.size());
        parents.forEach(
            parent -> {
              CommentBo comment = new CommentBo(parent);
              List<CommentReply> commentReplyByCoid = null;
              if (parent.getCreated() != null) {
                /*if (redisServer.hasKey(String.valueOf(parent.getCoid()))) {
                  // redis 获取回复
                  String s = redisServer.get(String.valueOf(parent.getCoid()));
                  commentReplyByCoid = JsonUtil.getStringToList(s, CommentReply.class);
                  logger.info("Get comment reply from redis success!");

                } */
                // 设置缓存
                commentReplyByCoid = commentReplyService.findCommentReplyByCoid(parent.getCoid());
                /* redisServer.set(
                    String.valueOf(parent.getCoid()), JsonUtil.getListToJson(commentReplyByCoid));
                logger.info("Set comment reply from redis success!");*/

                if (commentReplyByCoid != null) {
                  for (CommentReply reply : commentReplyByCoid) {

                    reply.setCreated(TimeUtil.getTimeStateNew(reply.getCreated()));
                  }

                  comment.setCommentReplies(commentReplyByCoid);
                }
                comment.setCreated(TimeUtil.getTimeStateNew(parent.getCreated()));
              }
              comments.add(comment);
            });
        returnBo.setList(comments);
      }
      return returnBo;
    }
    return null;
  }

  @Override
  public PageInfo<CommentVo> getCommentsWithPage(
      CommentVoExample commentVoExample, int page, int limit) {
    PageHelper.startPage(page, limit);
    List<CommentVo> commentVos = commentDao.selectByExampleWithBLOBs(commentVoExample);
    PageInfo<CommentVo> pageInfo = new PageInfo<>(commentVos);
    return pageInfo;
  }

  @Override
  public CommentVo getCommentById(Integer coid) {
    if (null != coid) {
      return commentDao.selectByPrimaryKey(coid);
    }
    return null;
  }

  @Override
  public void delete(Integer coid, Integer cid) {
    if (null == cid) {
      throw new TipException("主键为空");
    }
    try {

      int i = commentDao.deleteByPrimaryKey(coid);
      commentReplyService.deleteReply(coid);
      if (i == 0) {

        throw new TipException("删除失败");
      }
    } catch (Exception e) {
      logger.error("删除失败", e);
      throw new TipException("删除失败");
    }

    Article article = articleService.getArticleById(cid);
    if (null != article && article.getCommentsNum() > 0) {
      article.setCommentsNum(article.getCommentsNum() - 1);
      articleService.updateArticleById(article);
    }
  }

  @Override
  public void update(CommentVo comments) {}

  @Override
  public void updateAddLike(int coid) {

    try {

      int i = commentDao.updateAddLike(coid);
      if (i == 0) {

        throw new TipException("点赞失败");
      }
    } catch (Exception e) {
      logger.error("点赞失败", e);
      throw new TipException("点赞失败");
    }
  }

  @Override
  public int deleteCommentsById(Integer cid) {

    return commentDao.deleteCommentsById(cid);
  }

  /**
   * 检查评论输入数据
   *
   * @param comments
   * @throws TipException
   */
  private void checkComment(CommentVo comments) throws TipException {
    if (null == comments) {
      throw new TipException("评论对象为空");
    }
    if (StringUtils.isNotBlank(comments.getMail()) && !MyUtils.isEmail(comments.getMail())) {
      throw new TipException("请输入正确的邮箱格式");
    }
    if (StringUtils.isBlank(comments.getContent())) {
      throw new TipException("评论内容不能为空");
    }
    if (comments.getContent().length() < 1 || comments.getContent().length() > 2000) {
      throw new TipException("评论字符过多");
    }
    if (null == comments.getCid()) {
      throw new TipException("评论文章不能为空");
    }
  }

  /**
   * copy原有的分页信息，除数据
   *
   * @param ordinal
   * @param <T>
   * @return
   */
  private <T> PageInfo<T> copyPageInfo(PageInfo ordinal) {
    PageInfo<T> returnBo = new PageInfo<T>();
    returnBo.setPageSize(ordinal.getPageSize());
    returnBo.setPageNum(ordinal.getPageNum());
    returnBo.setEndRow(ordinal.getEndRow());
    returnBo.setTotal(ordinal.getTotal());
    returnBo.setHasNextPage(ordinal.isHasNextPage());
    returnBo.setHasPreviousPage(ordinal.isHasPreviousPage());
    returnBo.setIsFirstPage(ordinal.isIsFirstPage());
    returnBo.setIsLastPage(ordinal.isIsLastPage());
    returnBo.setNavigateFirstPage(ordinal.getNavigateFirstPage());
    returnBo.setNavigateLastPage(ordinal.getNavigateLastPage());
    returnBo.setNavigatepageNums(ordinal.getNavigatepageNums());
    returnBo.setSize(ordinal.getSize());
    returnBo.setPrePage(ordinal.getPrePage());
    returnBo.setNextPage(ordinal.getNextPage());
    return returnBo;
  }
}
