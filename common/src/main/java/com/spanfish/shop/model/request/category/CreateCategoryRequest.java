package com.spanfish.shop.model.request.category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryRequest {

  @NotNull private String name;
}
