package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Product;
import com.spanfish.shop.repository.ProductRepository;
import com.spanfish.shop.service.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Optional<Product> findById(Long productId) {
    return productRepository.findById(productId);
  }

  @Override
  public Page<Product> findAll(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @Override
  public Product create(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Product update(Product product) {
    return productRepository.save(product);
  }

  @Override
  public void delete(Long productId) {
    productRepository.deleteById(productId);
  }
}
