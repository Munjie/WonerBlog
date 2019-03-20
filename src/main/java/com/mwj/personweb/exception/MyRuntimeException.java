package com.mwj.personweb.exception;

/** @Author: 母哥 @Date: 2019-02-21 17:17 @Version 1.0 */
public class MyRuntimeException extends RuntimeException {

  private static final long serialVersionUID = -6925278824391495117L;

  public MyRuntimeException(String message) {
    super(message);
  }
}
