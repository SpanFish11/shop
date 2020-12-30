package com.spanfish.shop.exception;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  // 400
  @ExceptionHandler({InvalidArgumentException.class})
  protected ResponseEntity<Object> handleInvalidArgumentException(Exception ex) {
    final ExceptionResponse re = new ExceptionResponse(BAD_REQUEST);
    re.setMessage(ex.getMessage());
    return buildResponseEntity(re);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final ExceptionResponse re = new ExceptionResponse(BAD_REQUEST);
    re.setMessage("Validation error");
    re.addDetails(ex.getBindingResult());
    return buildResponseEntity(re);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      final HttpMessageNotReadableException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    log.info(
        "{} to {}",
        servletWebRequest.getHttpMethod(),
        servletWebRequest.getRequest().getServletPath());
    final String error = "Malformed JSON request";
    return buildResponseEntity(new ExceptionResponse(BAD_REQUEST, error, ex));
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      final NoHandlerFoundException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final ExceptionResponse re = new ExceptionResponse(BAD_REQUEST);
    re.setMessage(
        format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
    re.setDebugMessage(ex.getMessage());
    return buildResponseEntity(re);
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    final ExceptionResponse re = new ExceptionResponse(BAD_REQUEST);
    re.setMessage(
        format(
            "The parameter '%s' of value '%s' could not be converted to type '%s'",
            ex.getName(),
            ex.getValue(),
            Objects.requireNonNull(ex.getRequiredType()).getSimpleName()));
    re.setDebugMessage(ex.getMessage());
    return buildResponseEntity(re);
  }

  @ExceptionHandler({javax.validation.ConstraintViolationException.class})
  protected ResponseEntity<Object> handleConstraintViolationException(
      final javax.validation.ConstraintViolationException ex) {
    final ExceptionResponse re = new ExceptionResponse(BAD_REQUEST);
    re.setMessage("Validation error");
    re.addDetails(ex.getConstraintViolations());
    return buildResponseEntity(re);
  }

  // 404
  @ExceptionHandler({ResourceNotFoundException.class})
  protected ResponseEntity<Object> handleResourceNotFoundException(
      final Exception ex, final WebRequest request) {
    final ExceptionResponse re = new ExceptionResponse(NOT_FOUND);
    re.setMessage(ex.getMessage());
    return buildResponseEntity(re);
  }

  // 409
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolation(
      final DataIntegrityViolationException ex, final WebRequest request) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      return buildResponseEntity(new ExceptionResponse(CONFLICT, "Database error", ex.getCause()));
    }
    return buildResponseEntity(new ExceptionResponse(INTERNAL_SERVER_ERROR, ex));
  }

  // 415
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    return buildResponseEntity(
        new ExceptionResponse(
            UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
  }

  // 500
  @ExceptionHandler({Exception.class})
  protected ResponseEntity<Object> handleError(final Exception e) {
    final ExceptionResponse re = new ExceptionResponse(INTERNAL_SERVER_ERROR);
    re.setMessage(e.getClass().getName() + " " + e.getMessage());
    return buildResponseEntity(re);
  }

  private ResponseEntity<Object> buildResponseEntity(final ExceptionResponse exception) {
    return new ResponseEntity<>(exception, exception.getStatus());
  }
}
