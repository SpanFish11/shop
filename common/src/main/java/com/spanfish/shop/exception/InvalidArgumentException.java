package com.spanfish.shop.exception;

public class InvalidArgumentException extends RuntimeException {

  private static final long serialVersionUID = -6914564528778643413L;

  public InvalidArgumentException() {}

  public InvalidArgumentException(String message) {
    super(message);
  }

  public InvalidArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidArgumentException(Throwable cause) {
    super(cause);
  }
}
