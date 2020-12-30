package com.spanfish.shop.service.impl;

import static java.util.Calendar.MINUTE;
import static java.util.Calendar.getInstance;
import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;
import static org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.ResetPassword;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.PasswordResetToken;
import com.spanfish.shop.model.entity.VerificationEmailToken;
import com.spanfish.shop.model.event.PasswordResetEvent;
import com.spanfish.shop.model.event.VerificationEmailEvent;
import com.spanfish.shop.repository.PasswordResetTokenRepository;
import com.spanfish.shop.repository.VerificationEmailTokenRepository;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.TokenService;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private static final int EXPIRATION = 60 * 24;

  private final VerificationEmailTokenRepository verificationEmailTokenRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final CustomerService customerService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void createVerificationEmailToken(final Customer customer) {
    final String token = generateToken();

    final VerificationEmailToken verificationEmailToken =
        verificationEmailTokenRepository
            .findByCustomer(customer)
            .orElse(VerificationEmailToken.builder().customer(customer).build());
    verificationEmailToken.setToken(token);
    verificationEmailToken.setExpiration(generateExpirationTime());
    verificationEmailTokenRepository.save(verificationEmailToken);

    applicationEventPublisher.publishEvent(new VerificationEmailEvent(this, customer, token));
  }

  @Override
  public void validateVerificationEmailToken(final String token) {
    final VerificationEmailToken verificationToken =
        verificationEmailTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Verification Email Token not found"));

    final Customer customer = verificationToken.getCustomer();
    if (isNull(customer)) {
      throw new ResourceNotFoundException("Customer not found");
    }

    checkTokenExpire(verificationToken.getExpiration());
    customer.setEmailVerified(true);
    verificationEmailTokenRepository.deleteById(verificationToken.getId());
    customerService.updateCustomer(customer);
  }

  @Override
  public void createPasswordResetToken(final String email) {
    final Customer customer = customerService.findCustomerByEmailIgnoreCase(email);
    final String token = generateToken();

    final PasswordResetToken passwordResetToken =
        passwordResetTokenRepository
            .findByCustomer(customer)
            .orElse(PasswordResetToken.builder().customer(customer).build());
    passwordResetToken.setToken(token);
    passwordResetToken.setExpiration(generateExpirationTime());
    passwordResetTokenRepository.save(passwordResetToken);

    applicationEventPublisher.publishEvent(new PasswordResetEvent(this, customer, token));
  }

  @Override
  public void validatePasswordResetToken(final String token) {
    final PasswordResetToken passwordResetToken =
        passwordResetTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    checkTokenExpire(passwordResetToken.getExpiration());
  }

  @Override
  public void passwordReset(final ResetPassword resetPassword) {
    final PasswordResetToken passwordResetToken =
        passwordResetTokenRepository
            .findByToken(resetPassword.token())
            .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    checkTokenExpire(passwordResetToken.getExpiration());

    final Customer customer = passwordResetToken.getCustomer();

    if (isNull(customer)) {
      throw new ResourceNotFoundException("Customer not found");
    } else if (passwordEncoder.matches(resetPassword.newPassword(), customer.getPassword())) {
      return;
    }

    customer.setPassword(passwordEncoder.encode(resetPassword.newPassword()));
    passwordResetTokenRepository.deleteById(passwordResetToken.getId());
    customerService.updateCustomer(customer);
  }

  private Date generateExpirationTime() {
    final Calendar calendar = getInstance();
    calendar.setTime(new Timestamp(calendar.getTime().getTime()));
    calendar.add(MINUTE, EXPIRATION);
    return new Date(calendar.getTime().getTime());
  }

  private void checkTokenExpire(final Date date) {
    if ((date.getTime() - getInstance().getTime().getTime()) <= 0) {
      throw new InvalidArgumentException("Token is expired");
    }
  }

  private String generateToken() {
    return encodeBase64URLSafeString(randomUUID().toString().getBytes());
  }
}
