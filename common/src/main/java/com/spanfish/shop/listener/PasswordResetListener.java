package com.spanfish.shop.listener;

import com.spanfish.shop.event.PasswordResetEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetListener implements ApplicationListener<PasswordResetEvent> {

  private final JavaMailSender javaMailSender;

  private static final String SUBJECT = "Password Reset Confirmation";
  private static final String TEXT =
      "Hello, %s!,\nFollow the link to complete the registration: "
          + "http://localhost:8080/api/v1/accounts/password/forgot/reset-password-verification?token=%s";

  @Override
  public void onApplicationEvent(@SuppressWarnings("NullableProblems") PasswordResetEvent event) {
    this.makeMessage(event);
  }

  private void makeMessage(PasswordResetEvent event) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(SUBJECT);
    message.setTo(event.getCustomer().getEmail());
    message.setText(String.format(TEXT, event.getCustomer().getName(), event.getToken()));
    javaMailSender.send(message);
  }
}
