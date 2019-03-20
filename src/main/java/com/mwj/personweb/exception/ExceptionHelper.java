package com.mwj.personweb.exception;

import com.mwj.personweb.bo.RestResponseBo;
import org.slf4j.Logger;

/** @Author: 母哥 @Date: 2019-02-21 17:17 @Version 1.0 */
public class ExceptionHelper {
  /**
   * 统一处理异常
   *
   * @param logger
   * @param msg
   * @param e
   * @return
   */
  public static RestResponseBo handlerException(Logger logger, String msg, Exception e) {
    if (e instanceof TipException) {
      msg = e.getMessage();
    } else {
      logger.error(msg, e);
    }
    return RestResponseBo.fail(msg);
  }
}
