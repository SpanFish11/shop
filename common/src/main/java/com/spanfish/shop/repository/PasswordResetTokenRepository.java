package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

  Optional<PasswordResetToken> findByToken(String token);

  Optional<PasswordResetToken> findByCustomer_Id(Long id);
}
