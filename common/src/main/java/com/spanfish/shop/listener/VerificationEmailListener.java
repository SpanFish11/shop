package com.spanfish.shop.listener;

import static java.lang.String.format;

import com.spanfish.shop.model.event.VerificationEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationEmailListener implements ApplicationListener<VerificationEmailEvent> {

  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.host_address}")
  private static String hostAddress;

  private static final String SUBJECT = "Registration Confirmation";
  private static final String TEXT =
      "Hello, %s!,\nFollow the link to complete the registration: "
          + hostAddress
          + "/registration/email-verification?token=%s";

  @Override
  public void onApplicationEvent(@NonNull final VerificationEmailEvent event) {
    this.makeMessage(event);
  }

  private void makeMessage(final VerificationEmailEvent event) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(SUBJECT);
    message.setTo(event.getCustomer().getEmail());
    message.setText(format(TEXT, event.getCustomer().getName(), event.getToken()));
    javaMailSender.send(message);
  }
}
