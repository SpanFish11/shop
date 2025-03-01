package com.spanfish.shop.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("amazon.s3.images")
public class AmazonConfig {

  private String accessKeyId;
  private String secretAccessKey;
  private String region;
}
