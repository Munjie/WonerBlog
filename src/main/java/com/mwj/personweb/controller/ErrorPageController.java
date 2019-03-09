package com.mwj.personweb.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/** @Author: 母哥 @Date: 2019-03-04 17:47 @Version 1.0 */
@Controller
public class ErrorPageController implements ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    // 获取statusCode:401,404,500
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == 500) {

      return "error/500";

    } else if (statusCode == 404) {

      return "error/404";

    } else if (statusCode == 400) {

      return "error/400";

    } else {

      return null;
    }
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
