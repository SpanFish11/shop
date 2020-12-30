package com.spanfish.shop.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.CustomerMapper;
import com.spanfish.shop.model.dto.CustomerDTO;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.request.customer.ForgotPasswordRequest;
import com.spanfish.shop.model.request.customer.ForgotResetPasswordRequest;
import com.spanfish.shop.model.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.model.request.customer.TokenRequest;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final CustomerService customerService;
  private final TokenService tokenService;
  private final CustomerMapper mapper;

  @Operation(
      summary = "Creating customer",
      description = "Endpoint for creating customers",
      responses = {
        @ApiResponse(responseCode = "201", description = "Customer created"),
        @ApiResponse(responseCode = "400")
      })
  @PostMapping("/registration")
  public ResponseEntity<CustomerDTO> register(
      @RequestBody @Valid final RegisterCustomerRequest request) {
    final Customer customer = customerService.save(mapper.toCustomer(request));
    tokenService.createVerificationEmailToken(customer);
    return new ResponseEntity<>(mapper.toDTO(customer), CREATED);
  }

  @Operation(summary = "Email verification", description = "Endpoint for email verification")
  @PostMapping("/registration/email-verification")
  public ResponseEntity<HttpStatus> emailVerification(
      @RequestBody @Valid final TokenRequest tokenRequest) {
    tokenService.validateVerificationEmailToken(tokenRequest.getToken());
    return ok().build();
  }

  @Operation(summary = "Password forgot", description = "Endpoint for password reset request")
  @PostMapping("/password/forgot")
  public ResponseEntity<HttpStatus> forgotPassword(
      @RequestBody @Valid final ForgotPasswordRequest forgotPasswordRequest) {
    tokenService.createPasswordResetToken(forgotPasswordRequest.getEmail());
    return ok().build();
  }

  @Operation(
      summary = "Password reset token verification",
      description = "Endpoint for password reset token verification")
  @PostMapping("/password/forgot/reset-password-verification")
  public ResponseEntity<HttpStatus> passwordResetVerification(
      @RequestBody @Valid final TokenRequest tokenRequest) {
    tokenService.validatePasswordResetToken(tokenRequest.getToken());
    return ok().build();
  }

  @Operation(summary = "Password reset", description = "Endpoint for password reset")
  @PostMapping("/password/forgot/reset-password")
  public ResponseEntity<HttpStatus> resetPassword(
      @RequestBody @Valid final ForgotResetPasswordRequest request) {
    tokenService.passwordReset(mapper.toResetPassword(request));
    return ok().build();
  }
}
