package com.spanfish.shop;

import com.spanfish.shop.config.ApplicationBeans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
    scanBasePackages = "com.spanfish",
    exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@Import({ApplicationBeans.class})
public class ShopApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopApplication.class, args);
  }
}
