package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Contacts;
import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.repository.ContactsRepository;
import com.spanfish.shop.service.ContactsService;
import com.spanfish.shop.service.CustomerService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactsServiceImpl implements ContactsService {

	private final ContactsRepository contactsRepository;
	private final CustomerService customerService;

	@Override
	public Optional<Contacts> findContactByCustomerId(Long customerId) {
		return contactsRepository.findContactsByCustomer_Id(customerId);
	}

	@Override
	public Contacts update(Long customerId, Contacts contacts) {
		Optional<Customer> customer = customerService.getById(customerId);
		customer.ifPresent(contacts::setCustomer);
		return contactsRepository.save(contacts);
	}
}
