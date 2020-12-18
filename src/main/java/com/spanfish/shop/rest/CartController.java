package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Cart;
import com.spanfish.shop.repository.CartRepository;
import com.spanfish.shop.rest.request.CartItemResponse;
import com.spanfish.shop.service.CartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<Cart> addItemToCart(@RequestBody CartItemResponse cartItemResponse) {

    Cart cart =
        cartService.addToCart(cartItemResponse.getProductId(), cartItemResponse.getAmount());
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @PostMapping("/incr")
  public ResponseEntity<Cart> incrCartItem(@RequestBody CartItemResponse cartItemResponse) {
    Cart cart =
        cartService.incrCartItem(cartItemResponse.getProductId(), cartItemResponse.getAmount());
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @PostMapping("/decr")
  public ResponseEntity<Cart> decrCartItem(@RequestBody CartItemResponse cartItemResponse) {
    Cart cart =
        cartService.decrCartItem(cartItemResponse.getProductId(), cartItemResponse.getAmount());
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @DeleteMapping("/remove")
  public ResponseEntity<Cart> deleteCartItem(@RequestBody CartItemResponse cartItemResponse) {
    Cart cart = cartService.removeCartItem(cartItemResponse.getProductId());
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteCartItems() {
    cartService.clearCart();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
