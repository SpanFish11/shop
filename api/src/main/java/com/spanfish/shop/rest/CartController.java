package com.spanfish.shop.rest;

import com.spanfish.shop.model.entity.Cart;
import com.spanfish.shop.model.request.cart.CartItemRequest;
import com.spanfish.shop.service.CartService;
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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @GetMapping
  public ResponseEntity<Cart> getCart() {
    return new ResponseEntity<>(cartService.getCart(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Cart> addItemToCart(@RequestBody @Valid CartItemRequest request) {
    return new ResponseEntity<>(
        cartService.addToCart(request.getProductId(), request.getAmount()), HttpStatus.OK);
  }

  @PostMapping("/incr")
  public ResponseEntity<Cart> incrCartItem(@RequestBody @Valid CartItemRequest request) {
    return new ResponseEntity<>(
        cartService.incrCartItem(request.getProductId(), request.getAmount()), HttpStatus.OK);
  }

  @PostMapping("/decr")
  public ResponseEntity<Cart> decrCartItem(@RequestBody @Valid CartItemRequest request) {
    return new ResponseEntity<>(
        cartService.decrCartItem(request.getProductId(), request.getAmount()), HttpStatus.OK);
  }

  @DeleteMapping("/items/{id}")
  public ResponseEntity<Cart> deleteCartItem(@PathVariable("id") @Min(1) @NotNull Long id) {
    return new ResponseEntity<>(cartService.removeCartItem(id), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteCartItems() {
    cartService.clearCart();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
