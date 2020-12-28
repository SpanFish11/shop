package com.spanfish.shop.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AWSS3Service {

  String uploadImage(MultipartFile multipartFile);

  void deleteImage(String name);

  List<String> uploadImages(List<MultipartFile> multipartFileList);
}
