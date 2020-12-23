package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.request.customer.PasswordResetRequest;
import com.spanfish.shop.entity.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.entity.request.customer.UpdateCustomerAddressRequest;
import com.spanfish.shop.entity.request.customer.UpdateCustomerRequest;
import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.VerificationTokenService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final VerificationTokenService verificationTokenService;

  @GetMapping
  public ResponseEntity<Customer> get() {

    Customer customer = customerService.getCustomer();
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  @GetMapping("/activation")
  public ResponseEntity<HttpStatus> activate(@RequestParam("token") String token) {

    if (StringUtils.isEmpty(token)) {
      throw new InvalidArgumentException("Invalid activation code");
    }
    verificationTokenService.validateToken(token);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Customer> register(
      @RequestBody RegisterCustomerRequest registerCustomerRequest) {

    if (Objects.isNull(registerCustomerRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    Customer customer = customerService.save(registerCustomerRequest);
    verificationTokenService.createOrUpdateToken(customer);
    return new ResponseEntity<>(customer, HttpStatus.CREATED);
  }

  @PostMapping("/password/reset")
  public ResponseEntity<HttpStatus> resetPassword(
      @RequestBody PasswordResetRequest passwordResetRequest) {

    if (Objects.isNull(passwordResetRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    customerService.resetPassword(passwordResetRequest);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Customer> update(@RequestBody UpdateCustomerRequest updateCustomerRequest) {

    if (Objects.isNull(updateCustomerRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    Customer customer = customerService.update(updateCustomerRequest);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  @PutMapping("/address")
  public ResponseEntity<Customer> updateAddress(
      @RequestBody UpdateCustomerAddressRequest updateCustomerAddressRequest) {

    if (Objects.isNull(updateCustomerAddressRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    Customer customer = customerService.updateAddress(updateCustomerAddressRequest);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @DeleteMapping(value = "{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

    customerService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/all")
  public ResponseEntity<Page<Customer>> getAllCustomers(@PageableDefault Pageable pageable) {

    Page<Customer> customers = customerService.getAll(pageable);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid customer id");
    }
    Customer customer = customerService.getById(id);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }
}
