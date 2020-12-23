package com.spanfish.shop.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<RestException> handleResourceNotFoundException(Exception e) {
    log.error("Exception Caused By: ", e);
    return buildResponseEntity(
        new RestException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }

  @ExceptionHandler(InvalidArgumentException.class)
  public ResponseEntity<RestException> handleInvalidArgumentException(Exception e) {
    log.error("Exception Caused By: ", e);
    return buildResponseEntity(
        new RestException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestException> handleError(Exception e) {
    log.error("Exception Caused By: ", e);
    return buildResponseEntity(
        new RestException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            e.getClass().getName() + " " + e.getMessage()));
  }

  private ResponseEntity<RestException> buildResponseEntity(RestException exception) {
    return new ResponseEntity<>(exception, exception.getStatus());
  }
}
