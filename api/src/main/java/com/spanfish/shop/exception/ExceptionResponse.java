package com.spanfish.shop.exception;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.time.LocalDateTime.now;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Data
@JsonInclude(NON_NULL)
public class ExceptionResponse implements Serializable {

  @Serial private static final long serialVersionUID = -4917322328598919785L;

  private LocalDateTime timestamp;
  private HttpStatus status;
  private Integer error;
  private String message;
  private String debugMessage;
  private Map<String, Object> details;

  private ExceptionResponse() {
    timestamp = now();
  }

  public ExceptionResponse(final HttpStatus status) {
    this();
    this.status = status;
    this.error = status.value();
  }

  public ExceptionResponse(final HttpStatus status, final Throwable ex) {
    this();
    this.status = status;
    this.error = status.value();
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ExceptionResponse(final HttpStatus status, final String message, final Throwable ex) {
    this();
    this.status = status;
    this.error = status.value();
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  public void addDetails(final BindingResult bindingResult) {
    this.details = makeDetails(bindingResult);
  }

  public void addDetails(final Set<ConstraintViolation<?>> constraintViolations) {
    this.details = makeDetails(constraintViolations);
  }

  private Map<String, Object> makeDetails(final BindingResult bindingResult) {
    Map<String, Object> errors = new LinkedHashMap<>();
    bindingResult
        .getFieldErrors()
        .forEach(
            e -> {
              if (errors.containsKey(e.getField())) {
                errors.put(
                    e.getField(),
                    String.format("%s; %s", errors.get(e.getField()), e.getDefaultMessage()));
              } else {
                errors.put(e.getField(), e.getDefaultMessage());
              }
            });
    return errors;
  }

  private Map<String, Object> makeDetails(final Set<ConstraintViolation<?>> constraintViolations) {
    Map<String, Object> errors = new LinkedHashMap<>();
    constraintViolations.forEach(
        e -> {
          if (errors.containsKey(((PathImpl) e.getPropertyPath()).getLeafNode().asString())) {
            errors.put(
                ((PathImpl) e.getPropertyPath()).getLeafNode().asString(),
                String.format(
                    "%s; %s",
                    errors.get(((PathImpl) e.getPropertyPath()).getLeafNode().asString()),
                    e.getMessage()));
          } else {
            errors.put(((PathImpl) e.getPropertyPath()).getLeafNode().asString(), e.getMessage());
          }
        });
    return errors;
  }
}
