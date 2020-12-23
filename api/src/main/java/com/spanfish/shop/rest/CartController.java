package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Cart;
import com.spanfish.shop.entity.request.cart.CartItemRequest;
import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.repository.CartRepository;
import com.spanfish.shop.service.CartService;
import java.util.Objects;
import java.util.Optional;
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

    Optional<Cart> cartOptional = cartService.getCart();
    return cartOptional
        .map(cart -> new ResponseEntity<>(cart, HttpStatus.OK))
        .orElseThrow(() -> new ResourceNotFoundException("Could not find cart"));
  }

  @PostMapping
  public ResponseEntity<Cart> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {

    if (Objects.isNull(cartItemRequest)
        || cartItemRequest.getProductId() <= 0
        || cartItemRequest.getAmount() <= 0) {
      throw new InvalidArgumentException("Invalid product id, amount or cart item is null");
    }
    Cart cart = cartService.addToCart(cartItemRequest.getProductId(), cartItemRequest.getAmount());
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @PostMapping("/incr")
  public ResponseEntity<Cart> incrCartItem(@RequestBody CartItemRequest cartItemRequest) {

    if (Objects.isNull(cartItemRequest)
        || cartItemRequest.getProductId() <= 0
        || cartItemRequest.getAmount() <= 0) {
      throw new InvalidArgumentException("Invalid product id, amount or cart item is null");
    }
    Cart cart =
        cartService.incrCartItem(cartItemRequest.getProductId(), cartItemRequest.getAmount());
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @PostMapping("/decr")
  public ResponseEntity<Cart> decrCartItem(@RequestBody CartItemRequest cartItemRequest) {

    if (Objects.isNull(cartItemRequest)
        || cartItemRequest.getProductId() <= 0
        || cartItemRequest.getAmount() <= 0) {
      throw new InvalidArgumentException("Invalid product id, amount or cart item is null");
    }
    Cart cart =
        cartService.decrCartItem(cartItemRequest.getProductId(), cartItemRequest.getAmount());
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @DeleteMapping("/items/{id}")
  public ResponseEntity<Cart> deleteCartItem(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid cart item id");
    }
    Cart cart = cartService.removeCartItem(id);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteCartItems() {
    cartService.clearCart();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
