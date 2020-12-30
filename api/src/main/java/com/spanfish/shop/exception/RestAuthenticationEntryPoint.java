package com.spanfish.shop.exception;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      final HttpServletRequest httpServletRequest,
      final HttpServletResponse httpServletResponse,
      final AuthenticationException e)
      throws IOException {
    httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
    httpServletResponse.setStatus(SC_UNAUTHORIZED);
    httpServletResponse.getOutputStream().println(makeErrorMessage(e));
  }

  private String makeErrorMessage(final AuthenticationException e) {
    ExceptionResponse re = new ExceptionResponse(UNAUTHORIZED);
    re.setMessage("JWT token problem");
    re.setDebugMessage(e.getMessage());
    return new Gson().toJson(re);
  }
}
