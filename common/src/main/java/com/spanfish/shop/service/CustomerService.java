package com.spanfish.shop.service;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.request.customer.ResetPasswordRequest;
import com.spanfish.shop.entity.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.entity.request.customer.UpdateCustomerAddressRequest;
import com.spanfish.shop.entity.request.customer.UpdateCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  Page<Customer> getAll(Pageable pageable);

  Customer getCustomer();

  Customer getById(Long id);

  Customer findCustomerByEmailIgnoreCase(String email);

  Customer save(RegisterCustomerRequest registerCustomerRequest);

  Customer update(UpdateCustomerRequest updateCustomerRequest);

  Customer update(Customer customer);

  Customer updateAddress(UpdateCustomerAddressRequest updateCustomerAddressRequest);

  void delete(Long id);

  void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
