package com.home.exception;

public class MemberNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public MemberNotFoundException() {
  }

  public MemberNotFoundException(String message) {
    super(message);
  }

  public MemberNotFoundException(Throwable cause) {
    super(cause);
  }

  public MemberNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public MemberNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
  
}
