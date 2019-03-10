package com.mwj.personweb.controller;

import com.mwj.personweb.bo.RestResponseBo;
import com.mwj.personweb.exception.ExceptionHelper;
import com.mwj.personweb.exception.TipException;
import com.mwj.personweb.model.CommentVo;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ICommentService;
import com.mwj.personweb.service.ISysUserService;
import com.mwj.personweb.tdo.Types;
import com.mwj.personweb.utils.CommonUtil;
import com.mwj.personweb.utils.IPUtil;
import com.mwj.personweb.utils.MyUtils;
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
    String ref = request.getHeader("Referer");
    /* if (StringUtils.isBlank(ref) || StringUtils.isBlank(_csrf_token)) {
      return RestResponseBo.fail("Bad request");
    }

    String token = cache.hget(Types.CSRF_TOKEN.getType(), _csrf_token);
    if (StringUtils.isBlank(token)) {
      return RestResponseBo.fail("Bad request");
    }*/
    String headImg = null;
    if (null == cid || StringUtils.isBlank(text)) {
      return RestResponseBo.fail("请输入完整后评论");
    }
    if (StringUtils.isNotBlank(author) && author.length() > 50) {
      return RestResponseBo.fail("姓名过长");
    }

    if (StringUtils.isNotBlank(mail) && !MyUtils.isEmail(mail)) {
      return RestResponseBo.fail("请输入正确的邮箱格式");
    }

    if (text.length() > 200) {
      return RestResponseBo.fail("请输入200个字符以内的评论");
    }

    if (authentication != null && authentication.getName() != null) {
      author = authentication.getName();
      SysUser user = sysUserService.findByName(authentication.getName());
      mail = user.getEmail();
      headImg = user.getImgUrl();
    } else {
      headImg = CommonUtil.gravatarImg(mail);
    }

    String val = IPUtil.getIpAddrByRequest(request) + ":" + cid;
    Integer count = cache.hget(Types.COMMENTS_FREQUENCY.getType(), val);
    if (null != count && count > 0) {
      return RestResponseBo.fail("评论有点快噢");
    }

    // 去除js脚本
    author = MyUtils.cleanXSS(author);
    text = MyUtils.cleanXSS(text);

    author = EmojiParser.parseToAliases(author);
    text = EmojiParser.parseToAliases(text);

    CommentVo comments = new CommentVo();
    comments.setAuthor(author);
    comments.setCid(cid);
    comments.setIp(request.getRemoteAddr());
    comments.setContent(text);
    comments.setMail(mail);
    comments.setParent(coid);
    comments.setHeadImg(headImg);
    try {
      commentService.insertComment(comments);
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
