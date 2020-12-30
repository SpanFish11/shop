package com.spanfish.shop.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordFieldsValueMatchValidator
    implements ConstraintValidator<PasswordValueMatch, Object> {

  private String field;
  private String fieldMatch;
  private String message;

  @Override
  public void initialize(final PasswordValueMatch constraintAnnotation) {
    this.field = constraintAnnotation.field();
    this.fieldMatch = constraintAnnotation.fieldMatch();
    this.message = constraintAnnotation.message();
  }

  public boolean isValid(final Object value, final ConstraintValidatorContext context) {

    final Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
    final Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

    boolean isValid = false;
    if (fieldValue != null) {
      isValid = fieldValue.equals(fieldMatchValue);
    }

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(message)
          .addPropertyNode(field)
          .addConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(message)
          .addPropertyNode(fieldMatch)
          .addConstraintViolation();
    }

    return isValid;
  }
}
