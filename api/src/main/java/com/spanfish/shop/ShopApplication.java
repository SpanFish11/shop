package com.spanfish.shop;

import static org.springframework.boot.SpringApplication.run;

import com.spanfish.shop.config.AmazonConfig;
import com.spanfish.shop.config.ApplicationBeans;
import com.spanfish.shop.config.CachingConfiguration;
import com.spanfish.shop.config.MailConfiguration;
import com.spanfish.shop.config.SwaggerConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.spanfish")
@EnableCaching
@Import({
  ApplicationBeans.class,
  CachingConfiguration.class,
  SwaggerConfiguration.class,
  MailConfiguration.class,
  AmazonConfig.class
})
@RequiredArgsConstructor
public class ShopApplication {

  public static void main(String[] args) {
    run(ShopApplication.class, args);
  }
}
