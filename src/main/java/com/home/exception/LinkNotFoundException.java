package com.home.exception;

public class LinkNotFoundException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public LinkNotFoundException() {
    super();
  }

  public LinkNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public LinkNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public LinkNotFoundException(String message) {
    super(message);
  }

  public LinkNotFoundException(Throwable cause) {
    super(cause);
  }

}
