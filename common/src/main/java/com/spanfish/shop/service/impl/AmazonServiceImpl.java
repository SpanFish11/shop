package com.spanfish.shop.service.impl;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static software.amazon.awssdk.core.sync.RequestBody.fromBytes;
import static software.amazon.awssdk.services.s3.model.GetUrlRequest.builder;
import static software.amazon.awssdk.services.s3.model.ObjectCannedACL.PUBLIC_READ;

import com.spanfish.shop.service.AmazonService;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class AmazonServiceImpl implements AmazonService {

  private final S3Client s3Client;

  @Value("${amazon.s3.images.bucketName}")
  private String bucketName;

  @Value("${amazon.s3.images.productFolder}")
  private String productFolder;

  @Override
  public String uploadImage(final byte[] imageBytes, final Long productId) {

    final String imageUUID = randomUUID().toString();

    s3Client.putObject(
        PutObjectRequest.builder()
            .bucket(bucketName)
            .contentType("image/jpeg")
            .contentLength((long) imageBytes.length)
            .acl(PUBLIC_READ)
            .key(format("%s/%s/%s.jpg", productFolder, productId, imageUUID))
            .build(),
        fromBytes(imageBytes));

    final URL url =
        s3Client.utilities().getUrl(builder().bucket(bucketName).key(imageUUID).build());

    return url.toString() + ".jpg";
  }
}
