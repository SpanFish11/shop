package com.spanfish.shop.service;

import com.spanfish.shop.entity.Contacts;
import java.util.Optional;

public interface ContactsService {

  Optional<Contacts> findContactByCustomerId(Long customerId);

  Contacts update(Long customerId, Contacts contacts);
}
