package com.home.exception;

public class DuplicateMemberException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public DuplicateMemberException() {
  }

  public DuplicateMemberException(String message) {
    super(message);
  }

  public DuplicateMemberException(Throwable cause) {
    super(cause);
  }

  public DuplicateMemberException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateMemberException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  

}
