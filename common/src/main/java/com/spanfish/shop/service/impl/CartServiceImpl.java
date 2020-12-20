package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Cart;
import com.spanfish.shop.entity.CartItem;
import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.Product;
import com.spanfish.shop.repository.CartRepository;
import com.spanfish.shop.service.CartService;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.ProductService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final ProductService productService;
  private final CustomerService customerService;

  @Override
  public Optional<Cart> getCart() {

    Customer customer = customerService.getCustomer();
    Cart cart = customer.getCart();

    if (Objects.isNull(cart)) {
      cart = createCart(customer);
      customer.setCart(cart);
      cartRepository.save(cart);
    }
    return Optional.of(cart);
  }

  // TODO UserService
  @Override
  public Cart addToCart(Long productId, Integer amount) {

    Customer customer = customerService.getCustomer();
    Cart cart = customer.getCart();

    if (Objects.isNull(cart)) {
      cart = createCart(customer);
      customer.setCart(cart);
    }

    if (Objects.nonNull(cart.getCartItemList()) && !cart.getCartItemList().isEmpty()) {
      Optional<CartItem> cartItem =
          cart.getCartItemList().stream()
              .filter(ci -> ci.getProduct().getId().equals(productId))
              .findFirst();

      if (cartItem.isPresent()) {
        cartItem.get().setAmount(cartItem.get().getAmount() + amount);
        Cart updatedCart = calculateTotalPrice(cart);
        cart = cartRepository.save(updatedCart);
        return cart;
      }
    }

    Product product = productService.findById(productId).get();

    CartItem cartItem = CartItem.builder().cart(cart).amount(amount).product(product).build();

    cart.getCartItemList().add(cartItem);
    cart = cartRepository.save(calculateTotalPrice(cart));

    return cart;
  }

  @Override
  public Cart incrCartItem(Long productId, Integer amount) {

    Customer customer = customerService.getById(1L).get();
    Cart cart = customer.getCart();

    CartItem cartItem =
        cart.getCartItemList().stream()
            .filter(ci -> ci.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Item not found"));

    cartItem.setAmount(cartItem.getAmount() + amount);
    cart = cartRepository.save(calculateTotalPrice(cart));
    return cart;
  }

  @Override
  public Cart decrCartItem(Long productId, Integer amount) {

    Customer customer = customerService.getById(1L).get();
    Cart cart = customer.getCart();

    CartItem cartItem =
        cart.getCartItemList().stream()
            .filter(ci -> ci.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Item not found"));

    if (cartItem.getAmount() <= 0) {
      cart.getCartItemList().remove(cartItem);
      cart = cartRepository.save(calculateTotalPrice(cart));
      return cart;
    }

    cartItem.setAmount(cartItem.getAmount() - amount);
    cart = cartRepository.save(calculateTotalPrice(cart));
    return cart;
  }

  @Override
  public Cart removeCartItem(Long productId) {

    Customer customer = customerService.getById(1L).get();
    Cart cart = customer.getCart();

    CartItem cartItem =
        cart.getCartItemList().stream()
            .filter(ci -> ci.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Item not found"));

    cart.getCartItemList().remove(cartItem);

    if (cart.getCartItemList().isEmpty() || cart.getCartItemList().size() == 0) {
      cart.setTotalPrice(null);
      return cart;
    }

    cart = cartRepository.save(calculateTotalPrice(cart));
    return cart;
  }

  @Override
  public void clearCart() {

    Customer customer = customerService.getById(1L).get();
    Cart cart = customer.getCart();
    cart.getCartItemList().clear();
    cart.setTotalPrice(null);
    cartRepository.save(cart);
  }

  private Cart calculateTotalPrice(Cart cart) {
    cart.setTotalPrice(BigDecimal.ZERO);
    cart.getCartItemList()
        .forEach(
            cartItem ->
                cart.setTotalPrice(
                    cart.getTotalPrice()
                        .add(
                            cartItem
                                .getProduct()
                                .getPrice()
                                .multiply(new BigDecimal(cartItem.getAmount())))));
    return cart;
  }

  private Cart createCart(Customer customer) {
    return Cart.builder().customer(customer).cartItemList(new ArrayList<>()).build();
  }
}
