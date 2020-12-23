package com.spanfish.shop.repository;

import com.spanfish.shop.entity.VerificationToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

  Optional<VerificationToken> findByToken(String token);

  Optional<VerificationToken> findByCustomer_Id(Long id);
}
