package com.spanfish.shop.repository;

import com.spanfish.shop.entity.Contacts;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {

	Optional<Contacts> findContactsByCustomer_Id (Long customerId);
}
