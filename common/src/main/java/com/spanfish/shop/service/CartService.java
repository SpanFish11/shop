package com.spanfish.shop.service;

import com.spanfish.shop.entity.Cart;
import java.util.Optional;

public interface CartService {

  Optional<Cart> getCart();

  Cart addToCart(Long id, Integer amount);

  Cart incrCartItem(Long id, Integer amount);

  Cart decrCartItem(Long id, Integer amount);

  Cart removeCartItem(Long id);

  void clearCart();
}
