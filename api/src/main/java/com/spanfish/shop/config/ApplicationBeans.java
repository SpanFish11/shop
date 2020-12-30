package com.spanfish.shop.config;

import static software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create;
import static software.amazon.awssdk.regions.Region.of;
import static software.amazon.awssdk.services.s3.S3Client.builder;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.services.s3.S3Client;

public class ApplicationBeans {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JavaMailSender getJavaMailSender(final MailConfiguration configuration) {
    final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(configuration.getHost());
    mailSender.setPort(configuration.getPort());
    mailSender.setUsername(configuration.getUserName());
    mailSender.setPassword(configuration.getPassword());

    final Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtps");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    return mailSender;
  }

  @Bean
  public S3Client s3Client(final AmazonConfig amazonConfig) {
    return builder()
        .region(of(amazonConfig.getRegion()))
        .credentialsProvider(
            () -> create(amazonConfig.getAccessKeyId(), amazonConfig.getSecretAccessKey()))
        .build();
  }
}
