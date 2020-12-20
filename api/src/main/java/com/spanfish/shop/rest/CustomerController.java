package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.service.CustomerService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<Page<Customer>> getAll(@PageableDefault Pageable pageable) {

    Page<Customer> customers = customerService.getAll(pageable);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Customer> get(@PathVariable("id") Long customerId) {
    if (customerId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Customer> customerOptional = customerService.getById(customerId);
    return customerOptional
        .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<Customer> create(@RequestBody Customer requestCustomer) {

    if (requestCustomer == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Customer customer = customerService.save(requestCustomer);
    return new ResponseEntity<>(customer, HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Customer> update(@RequestBody Customer requestCustomer) {

    if (requestCustomer == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Customer customer = customerService.save(requestCustomer);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  //  @Secured(Roles.Code.ADMIN) ROLE_ADMIN
  @DeleteMapping(value = "{id}")
  public ResponseEntity<Customer> delete(@PathVariable Long id) {
    Optional<Customer> customer = customerService.getById(id);

    if (customer.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    customerService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
