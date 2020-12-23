package com.spanfish.shop.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RestException implements Serializable {

  private static final long serialVersionUID = -4917322328598919785L;

  private HttpStatus status;
  private Integer error;
  private String message;
}
