package com.spanfish.shop.api;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.CustomerMapper;
import com.spanfish.shop.model.dto.CustomerDTO;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.SystemRoles;
import com.spanfish.shop.model.request.customer.ResetPasswordRequest;
import com.spanfish.shop.model.request.customer.UpdateAddressRequest;
import com.spanfish.shop.model.request.customer.UpdateCustomerRequest;
import com.spanfish.shop.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private final CustomerService customerService;
  private final CustomerMapper mapper;

  @Operation(summary = "Get customer", description = "Endpoint for user information")
  @GetMapping
  public ResponseEntity<CustomerDTO> get() {
    final Customer customer = customerService.getCustomer();
    return ok(mapper.toDTO(customer));
  }

  @Operation(
      summary = "Update customer",
      description = "Endpoint for updating customer contact information")
  @PutMapping
  public ResponseEntity<CustomerDTO> update(
      @RequestBody @Valid final UpdateCustomerRequest request) {
    final Customer updated = mapper.toCustomer(request, customerService.getCustomer());
    return ok(mapper.toDTO(updated));
  }

  @Operation(summary = "Update address", description = "Endpoint customer address update")
  @PutMapping("/address")
  public ResponseEntity<CustomerDTO> updateAddress(
      @RequestBody @Valid final UpdateAddressRequest request) {
    final Customer updated = mapper.toCustomer(request, customerService.getCustomer());
    return ok(mapper.toDTO(updated));
  }

  @Operation(summary = "Reset password", description = "Endpoint to change customer password")
  @PostMapping("/password/reset")
  public ResponseEntity<HttpStatus> resetPassword(
      @RequestBody @Valid final ResetPasswordRequest request) {
    customerService.resetPassword(mapper.toCustomer(request));
    return ok().build();
  }

  @Operation(summary = "Delete customer", description = "Endpoint to delete customer account")
  @Secured({"ROLE_ADMIN"})
  @DeleteMapping("{customer_id}")
  public ResponseEntity<HttpStatus> delete(
      @PathVariable("customer_id") @Min(1) @NotNull final Long id) {
    customerService.delete(id);
    return noContent().build();
  }

  @Operation(summary = "Get all customers", description = "Endpoint to get a list of all customers")
  @Secured({"ROLE_ADMIN"})
  @GetMapping("/all")
  public ResponseEntity<Page<Customer>> getAllCustomers(final Pageable pageable) {
    final Page<Customer> customers = customerService.getAll(pageable);
    return ok(customers);
  }

  @Operation(
      summary = "Get customer",
      description = "Endpoint to get information about the customer of interest")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @GetMapping("/{customer_id}")
  public ResponseEntity<CustomerDTO> getCustomer(
      @PathVariable("customer_id") @Min(1) @NotNull final Long id) {
    final Customer customer = customerService.getById(id);
    return ok(mapper.toDTO(customer));
  }

  @Operation(summary = "Add role", description = "Endpoint to add rights to a customer")
  @Secured({"ROLE_ADMIN"})
  @PutMapping("/add-role/{customer_id}")
  public ResponseEntity<Object> addRole(
      @RequestParam("role") final SystemRoles systemRoles,
      @PathVariable("customer_id") final Long id) {
    customerService.addRole(id, systemRoles);
    return ok().build();
  }

  @Operation(summary = "Remove role", description = "Endpoint to remove rights to a customer")
  @Secured({"ROLE_ADMIN"})
  @PutMapping("/remove-role/{customer_id}")
  public ResponseEntity<Object> removeRole(
      @RequestParam("role") final SystemRoles systemRoles,
      @PathVariable("customer_id") final Long id) {
    customerService.removeRole(id, systemRoles);
    return ok().build();
  }
}
