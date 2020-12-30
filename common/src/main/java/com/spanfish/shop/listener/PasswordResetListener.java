package com.spanfish.shop.listener;

import static java.lang.String.format;

import com.spanfish.shop.model.event.PasswordResetEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetListener implements ApplicationListener<PasswordResetEvent> {

  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.host_address}")
  private static String hostAddress;

  private static final String SUBJECT = "Password Reset Confirmation";
  private static final String TEXT =
      "Hello, %s!,\nFollow the link to complete the registration: "
          + hostAddress
          + "/password/forgot/reset-password-verification?token=%s";

  @Override
  public void onApplicationEvent(@NonNull final PasswordResetEvent event) {
    this.makeMessage(event);
  }

  private void makeMessage(final PasswordResetEvent event) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(SUBJECT);
    message.setTo(event.getCustomer().getEmail());
    message.setText(format(TEXT, event.getCustomer().getName(), event.getToken()));
    javaMailSender.send(message);
  }
}
