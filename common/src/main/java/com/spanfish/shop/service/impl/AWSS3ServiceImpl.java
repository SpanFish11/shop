package com.spanfish.shop.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.spanfish.shop.service.AWSS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class AWSS3ServiceImpl implements AWSS3Service {

  private final TransferManager transferManager;

  @Value("${AWS.S3.bucket-name}")
  private String bucketName;

  @Override
  public String uploadImage(MultipartFile photo) {
    log.info("File upload in progress.");
    String fileUrl = null;
    try {
      final File file = convertMultiPartFileToFile(photo);
      final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();

      Upload upload = transferManager.upload(bucketName, uniqueFileName, file);
      log.info("Uploading file with name= " + uniqueFileName);

      upload.waitForCompletion();
      log.info("File upload is completed.");

      file.delete();

      URL url = transferManager.getAmazonS3Client().getUrl(bucketName, uniqueFileName);
      fileUrl = url.toString();
    } catch (final SdkClientException | InterruptedException e) {
      log.info("File upload is failed.");
      log.error("Error= {} while uploading file.", e.getMessage());
    }
    return fileUrl;
  }

  @Override
  public void deleteImage(String name) {
    log.info("Deleting file with name= " + name);
    transferManager.getAmazonS3Client().deleteObject(new DeleteObjectRequest(bucketName, name));
    log.info("File deleted successfully.");
  }

  // TODO
  @Override
  public List<String> uploadImages(List<MultipartFile> multipartFileList) {
    return null;
  }

  private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
    final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    try (final FileOutputStream outputStream = new FileOutputStream(file)) {
      outputStream.write(multipartFile.getBytes());
    } catch (final IOException ex) {
      log.error("Error  converting the multi-part file to file= {}", ex.getMessage());
    }
    return file;
  }

  // TODO
  private List<File> convertMultipartFilesToFiles(final List<MultipartFile> multipartFiles) {
    List<File> files = new ArrayList<>();
    multipartFiles.forEach(
        (file) -> files.add(new File(Objects.requireNonNull(file.getOriginalFilename()))));
    files.forEach(
        (file ->
            multipartFiles.forEach(
                mF -> {
                  try {
                    mF.transferTo(file);
                  } catch (IOException e) {
                    log.error("Error  converting the multi-part file to file= {}", e.getMessage());
                  }
                })));
    return files;
  }
}
