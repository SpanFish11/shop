package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.PasswordResetToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

  Optional<PasswordResetToken> findByToken(String token);

  Optional<PasswordResetToken> findByCustomer(Customer customer);
}
