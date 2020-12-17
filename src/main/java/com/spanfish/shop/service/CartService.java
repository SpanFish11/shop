package com.spanfish.shop.service;

import com.spanfish.shop.entity.Cart;

public interface CartService {

  Cart addToCart(Long id, Integer amount);

  Cart incrCartItem(Long id, Integer amount);

  Cart decrCartItem(Long id, Integer amount);

  Cart removeCartItem(Long id);
}
