package com.spanfish.shop.exception;

import java.io.Serial;

public class InvalidArgumentException extends RuntimeException {

  @Serial private static final long serialVersionUID = -6914564528778643413L;

  public InvalidArgumentException() {}

  public InvalidArgumentException(final String message) {
    super(message);
  }

  public InvalidArgumentException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidArgumentException(final Throwable cause) {
    super(cause);
  }
}
