package com.spanfish.shop.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;

public class AWSS3Config {

  @Value("${AWS.S3.access-key}")
  private String accessKey;

  @Value("${AWS.S3.secret-key}")
  private String secretKey;

  private final int maxUploadThreads = 5;

  @Bean
  public TransferManager getTransferManager() {
    long multipartUploadThreshold = 5 * 1024 * 1025;
    return TransferManagerBuilder.standard()
        .withS3Client(getS3client())
        .withMultipartUploadThreshold(multipartUploadThreshold)
        .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
        .build();
  }

  private AmazonS3 getS3client() {
    return AmazonS3ClientBuilder.standard()
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
        .withRegion(Regions.EU_CENTRAL_1)
        .build();
  }
}
