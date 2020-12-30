package com.spanfish.shop.exception;

import java.io.Serial;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

  @Serial private static final long serialVersionUID = -4696405488114208544L;

  public CustomAuthenticationException(final String msg, final Throwable cause) {
    super(msg, cause);
  }

  public CustomAuthenticationException(final String msg) {
    super(msg);
  }
}
