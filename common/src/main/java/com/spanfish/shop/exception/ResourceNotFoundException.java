package com.spanfish.shop.exception;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = -177234923809386248L;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(final String s) {
    super(s);
  }

  public ResourceNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(final Throwable cause) {
    super(cause);
  }
}
