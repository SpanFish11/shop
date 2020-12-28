package com.spanfish.shop.rest;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.mapper.CustomerMapper;
import com.spanfish.shop.model.dto.CustomerDTO;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.request.customer.ForgotPasswordRequest;
import com.spanfish.shop.model.request.customer.ForgotResetPasswordRequest;
import com.spanfish.shop.model.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.model.request.customer.TokenRequest;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final CustomerService customerService;
  private final TokenService tokenService;
  private final CustomerMapper customerMapper;

  @PostMapping("/registration")
  public ResponseEntity<CustomerDTO> register(
      @RequestBody RegisterCustomerRequest registerCustomerRequest) {

    if (Objects.isNull(registerCustomerRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    Customer customer = customerService.save(registerCustomerRequest);
    tokenService.createVerificationEmailToken(customer);
    return new ResponseEntity<>(customerMapper.toDTO(customer), HttpStatus.CREATED);
  }

  @PostMapping("/registration/email-verification")
  public ResponseEntity<HttpStatus> emailVerification(@RequestBody TokenRequest tokenRequest) {

    if (Objects.isNull(tokenRequest)) {
      throw new InvalidArgumentException("Invalid email verification token");
    }
    tokenService.validateVerificationEmailToken(tokenRequest.getToken());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/password/forgot")
  public ResponseEntity<HttpStatus> forgotPassword(
      @RequestBody ForgotPasswordRequest forgotPasswordRequest) {

    if (Objects.isNull(forgotPasswordRequest)) {
      throw new InvalidArgumentException("Invalid forgot password request");
    }
    tokenService.createPasswordResetToken(forgotPasswordRequest.getEmail());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/password/forgot/reset-password-verification")
  public ResponseEntity<HttpStatus> passwordResetVerification(
      @RequestBody TokenRequest tokenRequest) {

    if (Objects.isNull(tokenRequest)) {
      throw new InvalidArgumentException("Invalid password reset token");
    }
    tokenService.validatePasswordResetToken(tokenRequest.getToken());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/password/forgot/reset-password")
  public ResponseEntity<HttpStatus> resetPassword(
      @RequestBody ForgotResetPasswordRequest ForgotResetPasswordRequest) {

    if (Objects.isNull(ForgotResetPasswordRequest)) {
      throw new InvalidArgumentException("Invalid password reset request");
    }
    tokenService.passwordReset(ForgotResetPasswordRequest);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
