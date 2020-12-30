package com.spanfish.shop.mapper;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.model.ResetPassword;
import com.spanfish.shop.model.dto.CustomerDTO;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.request.customer.ForgotResetPasswordRequest;
import com.spanfish.shop.model.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.model.request.customer.ResetPasswordRequest;
import com.spanfish.shop.model.request.customer.UpdateAddressRequest;
import com.spanfish.shop.model.request.customer.UpdateCustomerRequest;
import com.spanfish.shop.service.CustomerService;
import java.util.Calendar;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
    componentModel = "spring",
    imports = {
      Calendar.class,
    })
public abstract class CustomerMapper {

  @Autowired protected CustomerService customerService;
  @Autowired protected PasswordEncoder passwordEncoder;

  @BeforeMapping
  protected void checkEmail(final RegisterCustomerRequest request) {
    if (customerService.existsByEmail(request.getEmail())) {
      throw new InvalidArgumentException(
          "An account already exists with this email: " + request.getEmail());
    }
  }

  @BeforeMapping
  protected void checkPassword(final ResetPasswordRequest request) {
    final Customer customer = customerService.getCustomer();
    if (!passwordEncoder.matches(request.getOldPassword(), customer.getPassword())) {
      throw new InvalidArgumentException("Invalid password");
    }
  }

  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "password", source = "password")
  @Mapping(target = "registrationDate", expression = "java(Calendar.getInstance().getTime())")
  public abstract Customer toCustomer(final RegisterCustomerRequest request);

  @Mapping(target = "name", source = "name")
  @Mapping(target = "contacts.phone", source = "phone")
  public abstract Customer toCustomer(
      final UpdateCustomerRequest request, @MappingTarget Customer customer);

  @Mapping(target = "contacts.city", source = "city")
  @Mapping(target = "contacts.zip", source = "zip")
  @Mapping(target = "contacts.address", source = "address")
  public abstract Customer toCustomer(
      final UpdateAddressRequest request, @MappingTarget Customer customer);

  @Mapping(target = "password", source = "newPassword")
  public abstract Customer toCustomer(ResetPasswordRequest request);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "registrationDate", target = "registrationDate")
  @Mapping(source = "contacts.phone", target = "phone")
  @Mapping(source = "contacts.city", target = "city")
  @Mapping(source = "contacts.address", target = "address")
  @Mapping(source = "contacts.zip", target = "zipCode")
  public abstract CustomerDTO toDTO(final Customer source);

  @Mapping(target = "token", source = "token")
  @Mapping(target = "newPassword", source = "newPassword")
  public abstract ResetPassword toResetPassword(final ForgotResetPasswordRequest request);
}
