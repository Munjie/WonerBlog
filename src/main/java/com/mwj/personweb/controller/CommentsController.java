package com.mwj.personweb.controller;

import com.mwj.personweb.bo.RestResponseBo;
import com.mwj.personweb.exception.ExceptionHelper;
import com.mwj.personweb.exception.TipException;
import com.mwj.personweb.model.*;
import com.mwj.personweb.pojo.GeoLocation;
import com.mwj.personweb.service.*;
import com.mwj.personweb.service.IPService.GeoLocationService;
import com.mwj.personweb.tdo.Types;
import com.mwj.personweb.utils.CommonUtil;
import com.mwj.personweb.utils.IPUtil;
import com.mwj.personweb.utils.MyUtils;
import com.mwj.personweb.utils.UUID;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

/** @Auther: munjie @Date: 2019/3/10 12:59 @Description: */
@Controller
public class CommentsController extends AbstractController {

  private Logger logger = LoggerFactory.getLogger(CommentsController.class);

  @Autowired private ICommentService commentService;

  @Autowired private ISysUserService sysUserService;

  @Autowired private ILikeService likeService;

  @Autowired private ICommentReplyService commentReplyService;

  @Autowired private IMessageService messageService;

  @Autowired private IArticleService articleService;

  @Autowired private GeoLocationService geoLocationService;
  /**
   * @description //发表评论
   * @param:
   * @return:
   * @date: 2019/3/14 11:29
   */
  @PostMapping(value = "/comment")
  @ResponseBody
  @Transactional(rollbackFor = TipException.class)
  public RestResponseBo comment(
      HttpServletRequest request,
      HttpServletResponse response,
      Integer cid,
      Integer coid,
      String author,
      String mail,
      String text,
      String _csrf_token,
      Authentication authentication) {
    String headImg = null;
    int authoId = 0;
    CommentVo comments = new CommentVo();
    Message message = new Message();
    String ip = IPUtil.getIpAddrByRequest(request);

    String agent = IPUtil.getAgentByRequest(request);

    GeoLocation locationFromRequest = geoLocationService.getLocationFromRequest(comments.getIp());

    if (null == cid || StringUtils.isBlank(text)) {
      return RestResponseBo.fail("评论不能为空哦");
    }
    if (StringUtils.isNotBlank(author) && author.length() > 50) {
      return RestResponseBo.fail("姓名过长");
    }

    if (StringUtils.isNotBlank(mail) && !MyUtils.isEmail(mail)) {
      return RestResponseBo.fail("请输入正确的邮箱格式");
    }

    if (text.length() > 500) {
      return RestResponseBo.fail("请输入500个字符以内的评论");
    }

    if (authentication != null && authentication.getName() != null) {
      author = authentication.getName();
      SysUser user = sysUserService.findByName(authentication.getName());
      mail = user.getEmail();
      headImg = user.getImgUrl();
      authoId = user.getId();

    } else {
      headImg = CommonUtil.gravatarImg(mail);
      authoId = UUID.random(1, 100000);
      if (locationFromRequest != null && locationFromRequest.getCity() != null) {
        author = locationFromRequest.getCity() + "网友";
      } else {
        author = MyUtils.getRandomJianHan(4);
      }
    }

    String val = IPUtil.getIpAddrByRequest(request) + ":" + cid;
    Integer count = cache.hget(Types.COMMENTS_FREQUENCY.getType(), val);
    if (null != count && count > 0) {
      return RestResponseBo.fail("评论太快噢");
    }

    // 去除js脚本
    author = MyUtils.cleanXSS(author);
    text = MyUtils.cleanXSS(text);

    author = EmojiParser.parseToAliases(author);
    // text = EmojiParser.parseToAliases(text);

    comments.setAuthor(author);
    comments.setCid(cid);
    comments.setIp(request.getRemoteAddr());
    comments.setContent(text);
    comments.setMail(mail);
    comments.setParent(coid);
    comments.setHeadImg(headImg);
    comments.setIp(ip);
    comments.setAgent(agent);
    comments.setAuthorId(authoId);
    // 未读消息
    message.setArticleid(cid);
    message.setStatus("1");
    message.setHdimg(headImg);
    message.setComname(author);
    message.setCreattime(String.valueOf(System.currentTimeMillis()));
    if (text.length() > 20) {
      message.setMsg(text.substring(0, 10) + ".....");
    } else {
      message.setMsg(text);
    }
    message.setSysuser(articleService.findArticleAutor(cid));

    try {
      commentService.insertComment(comments);
      messageService.addMessage(message);
      cookie(
          "tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
      cookie("tale_remember_mail", URLDecoder.decode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);

      // 设置对每个文章1分钟评论一次
      cache.hset(Types.COMMENTS_FREQUENCY.getType(), val, 1, 60);
      return RestResponseBo.ok();
    } catch (Exception e) {
      String msg = "评论失败";
      return ExceptionHelper.handlerException(logger, msg, e);
    }
  }
  /**
   * @description // 评论点赞
   * @param:
   * @return:
   * @date: 2019/3/13 16:51
   */
  @PostMapping(value = "/likeComment")
  @ResponseBody
  @Transactional(rollbackFor = TipException.class)
  public RestResponseBo likeComment(
      HttpServletRequest request, Authentication authentication, CommentLike commentLike) {
    try {

      int authorId = 0;
      if (authentication != null && authentication.getName() != null) {

        SysUser user = sysUserService.findByName(authentication.getName());
        authorId = user.getId();

        CommentLike commentLikeIsAuth =
            likeService.findCommentLikeIsAuth(commentLike.getCoid(), authorId);
        if (commentLikeIsAuth == null) {
          commentLike.setAuthorId(authorId);
        } else {
          throw new TipException("你已经点过赞了噢");
        }
      } else {

        String ip = IPUtil.getIpAddrByRequest(request);
        String agent = IPUtil.getAgentByRequest(request);
        Integer coid = commentLike.getCoid();

        CommentLike commentLikeNotAuth = likeService.findCommentLikeNotAuth(coid, ip, agent);
        if (commentLikeNotAuth == null) {
          authorId = UUID.random(1, 100000);
          commentLike.setAgent(agent);
          commentLike.setIp(ip);
          commentLike.setAuthorId(authorId);

        } else {
          throw new TipException("你已经点过赞了噢");
        }
      }
      likeService.addCommentLike(commentLike);
      return RestResponseBo.ok();

    } catch (Exception e) {

      String msg = "点赞失败";
      return ExceptionHelper.handlerException(logger, msg, e);
    }
  }
  /**
   * @description //回复评论
   * @param:
   * @return:
   * @date: 2019/3/14 11:28
   */
  @PostMapping(value = "/replyComment")
  @ResponseBody
  @Transactional(rollbackFor = TipException.class)
  public RestResponseBo replyComment(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication,
      CommentReply commentReply) {

    String ip = IPUtil.getIpAddrByRequest(request);
    int authorId = 0;
    String authorName = null;
    String authorImg = null;
    GeoLocation locationFromRequest = geoLocationService.getLocationFromRequest(ip);

    try {
      if (authentication != null && authentication.getName() != null) {
        SysUser user = sysUserService.findByName(authentication.getName());
        authorId = user.getId();
        authorName = user.getName();
        authorImg = user.getImgUrl();
      } else {
        if (locationFromRequest != null && locationFromRequest.getCity() != null) {
          authorName = locationFromRequest.getCity() + "网友";
        } else {
          authorName = MyUtils.getRandomJianHan(4);
        }
        authorId = UUID.random(1, 100000);
        authorImg = "https://secure.gravatar.com/avatar";
      }
      commentReply.setAuthorId(authorId);
      commentReply.setAuthorName(authorName);
      commentReply.setCreated(String.valueOf(System.currentTimeMillis()));
      commentReply.setAuthorImg(authorImg);
      commentReplyService.addReplyComment(commentReply);

      return RestResponseBo.ok();
    } catch (Exception e) {

      String msg = "回复失败";
      return ExceptionHelper.handlerException(logger, msg, e);
    }
  }

  /**
   * @description // 回复点赞
   * @param:
   * @return:
   * @date: 2019/3/13 16:51
   */
  @PostMapping(value = "/likeReply")
  @ResponseBody
  @Transactional(rollbackFor = TipException.class)
  public RestResponseBo likeReply(
      HttpServletRequest request, Authentication authentication, CommentLike commentLike) {
    try {

      int authorId = 0;
      if (authentication != null && authentication.getName() != null) {

        SysUser user = sysUserService.findByName(authentication.getName());
        authorId = user.getId();

        CommentLike commentLikeIsAuth =
            likeService.findCommentLikeIsAuth(commentLike.getCoid(), authorId);
        if (commentLikeIsAuth == null) {
          commentLike.setAuthorId(authorId);
        } else {
          throw new TipException("你已经点过赞了噢");
        }
      } else {

        String ip = IPUtil.getIpAddrByRequest(request);
        String agent = IPUtil.getAgentByRequest(request);
        Integer coid = commentLike.getCoid();
        CommentLike commentLikeNotAuth = likeService.findCommentLikeNotAuth(coid, ip, agent);
        if (commentLikeNotAuth == null) {
          authorId = UUID.random(1, 100000);
          commentLike.setAgent(agent);
          commentLike.setIp(ip);
          commentLike.setAuthorId(authorId);

        } else {
          throw new TipException("你已经点过赞了噢");
        }
      }
      likeService.addReplyLike(commentLike);
      return RestResponseBo.ok();

    } catch (Exception e) {

      String msg = "点赞失败";
      return ExceptionHelper.handlerException(logger, msg, e);
    }
  }
  /**
   * 设置cookie
   *
   * @param name
   * @param value
   * @param maxAge
   * @param response
   */
  private void cookie(String name, String value, int maxAge, HttpServletResponse response) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(maxAge);
    cookie.setSecure(false);
    response.addCookie(cookie);
  }
}
