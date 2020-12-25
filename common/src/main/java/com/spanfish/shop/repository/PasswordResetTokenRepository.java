package com.spanfish.shop.repository;

import com.spanfish.shop.entity.PasswordResetToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

  Optional<PasswordResetToken> findByToken(String token);

  Optional<PasswordResetToken> findByCustomer_Id(Long id);
}
