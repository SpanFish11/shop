package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.VerificationEmailToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationEmailTokenRepository
    extends CrudRepository<VerificationEmailToken, Long> {

  Optional<VerificationEmailToken> findByToken(String token);

  Optional<VerificationEmailToken> findByCustomer_Id(Long id);
}
