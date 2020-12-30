package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.VerificationEmailToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface VerificationEmailTokenRepository
    extends CrudRepository<VerificationEmailToken, Long> {

  Optional<VerificationEmailToken> findByToken(String token);

  Optional<VerificationEmailToken> findByCustomer(Customer customer);
}
