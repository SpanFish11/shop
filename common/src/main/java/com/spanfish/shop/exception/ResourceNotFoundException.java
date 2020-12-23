package com.spanfish.shop.exception;

public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -177234923809386248L;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(String s) {
    super(s);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }
}
