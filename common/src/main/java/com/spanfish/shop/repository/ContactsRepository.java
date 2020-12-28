package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {

  Optional<Contacts> findContactsByCustomer_Id(Long customerId);
}
