package com.home.exception;

public class IdPasswordNotMatchingException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public IdPasswordNotMatchingException() {
    super();
  }

  public IdPasswordNotMatchingException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public IdPasswordNotMatchingException(String message, Throwable cause) {
    super(message, cause);
  }

  public IdPasswordNotMatchingException(String message) {
    super(message);
  }

  public IdPasswordNotMatchingException(Throwable cause) {
    super(cause);
  }
  
  

}
