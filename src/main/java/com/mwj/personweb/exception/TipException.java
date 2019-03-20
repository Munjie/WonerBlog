package com.mwj.personweb.exception;
/** @Author: 母哥 @Date: 2019-02-21 17:17 @Version 1.0 */
public class TipException extends RuntimeException {
  public TipException() {}

  public TipException(String message) {
    super(message);
  }

  public TipException(String message, Throwable cause) {
    super(message, cause);
  }

  public TipException(Throwable cause) {
    super(cause);
  }
}
