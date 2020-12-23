package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.VerificationToken;
import com.spanfish.shop.event.VerificationEmailEvent;
import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.repository.VerificationTokenRepository;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.VerificationTokenService;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

  private static final int EXPIRATION = 60 * 24;

  private final VerificationTokenRepository verificationTokenRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final CustomerService customerService;

  @Override
  public void createOrUpdateToken(Customer customer) {

    final String token = generateToken();

    Optional<VerificationToken> optionalVerificationToken =
        verificationTokenRepository.findByCustomer_Id(customer.getId());

    if (optionalVerificationToken.isEmpty()) {

      VerificationToken verificationToken = new VerificationToken();
      verificationToken.setToken(token);
      verificationToken.setCustomer(customer);
      verificationToken.setExpiration(generateExpirationTime());
      verificationTokenRepository.save(verificationToken);
    } else {

      optionalVerificationToken.get().setToken(token);
      optionalVerificationToken.get().setExpiration(generateExpirationTime());
      verificationTokenRepository.save(optionalVerificationToken.get());
    }

    VerificationEmailEvent verificationEmailEvent =
        new VerificationEmailEvent(this, customer, token);
    applicationEventPublisher.publishEvent(verificationEmailEvent);
  }

  @Override
  public void validateToken(String token) {

    VerificationToken verificationToken =
        verificationTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Null verification token"));

    Customer customer = verificationToken.getCustomer();
    if (Objects.isNull(customer)) {
      throw new ResourceNotFoundException("Customer not found");
    }

    checkTokenExpire(verificationToken.getExpiration());
    customer.setEmailVerified(true);
    verificationTokenRepository.deleteById(verificationToken.getId());
    customerService.update(customer);
  }

  private Date generateExpirationTime() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Timestamp(cal.getTime().getTime()));
    cal.add(Calendar.MINUTE, EXPIRATION);
    return new Date(cal.getTime().getTime());
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
