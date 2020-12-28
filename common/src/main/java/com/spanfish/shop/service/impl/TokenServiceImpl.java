package com.spanfish.shop.service.impl;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.PasswordResetToken;
import com.spanfish.shop.model.entity.VerificationEmailToken;
import com.spanfish.shop.model.event.PasswordResetEvent;
import com.spanfish.shop.model.event.VerificationEmailEvent;
import com.spanfish.shop.model.request.customer.ForgotResetPasswordRequest;
import com.spanfish.shop.repository.PasswordResetTokenRepository;
import com.spanfish.shop.repository.VerificationEmailTokenRepository;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

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
  public void createVerificationEmailToken(Customer customer) {
    final String token = generateToken();

    VerificationEmailToken verificationEmailToken =
        verificationEmailTokenRepository
            .findByCustomer_Id(customer.getId())
            .orElse(VerificationEmailToken.builder().customer(customer).build());
    verificationEmailToken.setToken(token);
    verificationEmailToken.setExpiration(generateExpirationTime());
    verificationEmailTokenRepository.save(verificationEmailToken);

    applicationEventPublisher.publishEvent(new VerificationEmailEvent(this, customer, token));
  }

  @Override
  public void validateVerificationEmailToken(String token) {
    VerificationEmailToken verificationToken =
        verificationEmailTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Verification Email Token not found"));

    Customer customer = verificationToken.getCustomer();
    if (Objects.isNull(customer)) {
      throw new ResourceNotFoundException("Customer not found");
    }

    checkTokenExpire(verificationToken.getExpiration());
    customer.setEmailVerified(true);
    verificationEmailTokenRepository.deleteById(verificationToken.getId());
    customerService.updateCustomer(customer);
  }

  @Override
  public void createPasswordResetToken(String email) {
    Customer customer = customerService.findCustomerByEmailIgnoreCase(email);
    final String token = generateToken();

    PasswordResetToken passwordResetToken =
        passwordResetTokenRepository
            .findByCustomer_Id(customer.getId())
            .orElse(PasswordResetToken.builder().customer(customer).build());
    passwordResetToken.setToken(token);
    passwordResetToken.setExpiration(generateExpirationTime());
    passwordResetTokenRepository.save(passwordResetToken);

    applicationEventPublisher.publishEvent(new PasswordResetEvent(this, customer, token));
  }

  @Override
  public void validatePasswordResetToken(String token) {
    PasswordResetToken passwordResetToken =
        passwordResetTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    checkTokenExpire(passwordResetToken.getExpiration());
  }

  @Override
  public void passwordReset(ForgotResetPasswordRequest forgotResetPasswordRequest) {
    PasswordResetToken passwordResetToken =
        passwordResetTokenRepository
            .findByToken(forgotResetPasswordRequest.getToken())
            .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    checkTokenExpire(passwordResetToken.getExpiration());

    Customer customer = passwordResetToken.getCustomer();

    if (Objects.isNull(customer)) {
      throw new ResourceNotFoundException("Customer not found");
    } else if (passwordEncoder.matches(
        forgotResetPasswordRequest.getNewPassword(), customer.getPassword())) {
      return;
    }

    customer.setPassword(passwordEncoder.encode(forgotResetPasswordRequest.getNewPassword()));
    passwordResetTokenRepository.deleteById(passwordResetToken.getId());
    customerService.updateCustomer(customer);
  }

  private Date generateExpirationTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Timestamp(calendar.getTime().getTime()));
    calendar.add(Calendar.MINUTE, EXPIRATION);
    return new Date(calendar.getTime().getTime());
  }

  private void checkTokenExpire(Date date) {
    if ((date.getTime() - Calendar.getInstance().getTime().getTime()) <= 0) {
      throw new InvalidArgumentException("Token is expired");
    }
  }

  private String generateToken() {
    return Base64.encodeBase64URLSafeString(UUID.randomUUID().toString().getBytes());
  }
}
