package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Cart;
import com.spanfish.shop.entity.request.cart.CartItemRequest;
import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.repository.CartRepository;
import com.spanfish.shop.service.CartService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;
  private final CartRepository cartRepository;

  @GetMapping
  public ResponseEntity<Cart> getCart() {
    return new ResponseEntity<>(cartService.getCart(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Cart> addItemToCart(@RequestBody CartItemRequest request) {
    if (Objects.isNull(request) || request.getProductId() <= 0 || request.getAmount() <= 0) {
      throw new InvalidArgumentException("Invalid product id, amount or cart item is null");
    }
    return new ResponseEntity<>(
        cartService.addToCart(request.getProductId(), request.getAmount()), HttpStatus.OK);
  }

  @PostMapping("/incr")
  public ResponseEntity<Cart> incrCartItem(@RequestBody CartItemRequest request) {
    if (Objects.isNull(request) || request.getProductId() <= 0 || request.getAmount() <= 0) {
      throw new InvalidArgumentException("Invalid product id, amount or cart item is null");
    }
    return new ResponseEntity<>(
        cartService.incrCartItem(request.getProductId(), request.getAmount()), HttpStatus.OK);
  }

  @PostMapping("/decr")
  public ResponseEntity<Cart> decrCartItem(@RequestBody CartItemRequest request) {
    if (Objects.isNull(request) || request.getProductId() <= 0 || request.getAmount() <= 0) {
      throw new InvalidArgumentException("Invalid product id, amount or cart item is null");
    }
    return new ResponseEntity<>(
        cartService.decrCartItem(request.getProductId(), request.getAmount()), HttpStatus.OK);
  }

  @DeleteMapping("/items/{id}")
  public ResponseEntity<Cart> deleteCartItem(@PathVariable("id") Long id) {
    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid cart item id");
    }
    return new ResponseEntity<>(cartService.removeCartItem(id), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteCartItems() {
    cartService.clearCart();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
