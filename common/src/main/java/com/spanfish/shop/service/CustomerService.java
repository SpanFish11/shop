package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.model.request.customer.ResetPasswordRequest;
import com.spanfish.shop.model.request.customer.UpdateAddressRequest;
import com.spanfish.shop.model.request.customer.UpdateCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  Page<Customer> getAll(Pageable pageable);

  Customer getCustomer();

  Customer getById(Long id);

  Customer findCustomerByEmailIgnoreCase(String email);

  Customer save(RegisterCustomerRequest registerCustomerRequest);

  Customer update(UpdateCustomerRequest updateCustomerRequest);

  void updateCustomer(Customer customer);

  Customer updateAddress(UpdateAddressRequest updateAddressRequest);

  void delete(Long id);

  void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
