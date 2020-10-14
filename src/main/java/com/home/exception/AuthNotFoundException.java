package com.home.exception;

public class AuthNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AuthNotFoundException() {
  }

  public AuthNotFoundException(String message) {
    super(message);
  }

  public AuthNotFoundException(Throwable cause) {
    super(cause);
  }

  public AuthNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
  
}
