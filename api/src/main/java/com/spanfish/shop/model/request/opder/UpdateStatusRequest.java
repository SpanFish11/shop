package com.spanfish.shop.model.request.opder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {

  @NotNull @NotBlank private String code;

  @Min(0)
  @Max(4)
  private Integer status;
}
