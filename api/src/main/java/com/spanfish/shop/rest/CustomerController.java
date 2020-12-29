package com.spanfish.shop.rest;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.request.customer.ResetPasswordRequest;
import com.spanfish.shop.model.request.customer.UpdateAddressRequest;
import com.spanfish.shop.model.request.customer.UpdateCustomerRequest;
import com.spanfish.shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<Customer> get() {
    return new ResponseEntity<>(customerService.getCustomer(), HttpStatus.OK);
  }

  @PostMapping("/password/reset")
  public ResponseEntity<HttpStatus> resetPassword(
      @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {

    if (Objects.isNull(resetPasswordRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    customerService.resetPassword(resetPasswordRequest);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Customer> update(
      @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {

    return new ResponseEntity<>(customerService.update(updateCustomerRequest), HttpStatus.OK);
  }

  @PutMapping("/address")
  public ResponseEntity<Customer> updateAddress(
      @RequestBody @Valid UpdateAddressRequest updateAddressRequest) {

    return new ResponseEntity<>(customerService.updateAddress(updateAddressRequest), HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") @Min(1) @NotNull Long id) {

    customerService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/all")
  public ResponseEntity<Page<Customer>> getAllCustomers(Pageable pageable) {

    Page<Customer> customers = customerService.getAll(pageable);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable("id") @Min(1) @NotNull Long id) {
    return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
  }
}
