package com.mwj.personweb.controller;

import com.mwj.personweb.utils.MapCache;

import javax.servlet.http.HttpServletRequest;

/** 抽象类controller 用于统一渲染页面url，页面名称，获取session中的用户 */
public abstract class AbstractController {

  protected MapCache cache = MapCache.single();

  /**
   * 主页的页面主题
   *
   * @param
   * @return
   */
  public AbstractController title(HttpServletRequest request, String title) {
    request.setAttribute("title", title);
    return this;
  }

  public AbstractController keywords(HttpServletRequest request, String keywords) {
    request.setAttribute("keywords", keywords);
    return this;
  }

  public String render_404() {
    return "comm/error_404";
  }
}
