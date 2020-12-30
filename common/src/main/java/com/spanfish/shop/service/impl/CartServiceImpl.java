package com.spanfish.shop.service.impl;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Cart;
import com.spanfish.shop.model.entity.CartItem;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.repository.CartRepository;
import com.spanfish.shop.service.CartService;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.ProductService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private static final String EXCEPTION_MESSAGE = "Could not find any cart item with the ID %d";

  private final CartRepository cartRepository;
  private final ProductService productService;
  private final CustomerService customerService;

  @Override
  public Cart getCart() {
    final Customer customer = customerService.getCustomer();
    Cart cart = customer.getCart();

    if (isNull(cart)) {
      cart = createCart(customer);
      customer.setCart(cart);
      cartRepository.save(cart);
    }
    return cart;
  }

  @Override
  public Cart addToCart(final Long productId, final Integer amount) {
    Cart cart = getCart();

    if (nonNull(cart.getCartItemList()) && !cart.getCartItemList().isEmpty()) {
      final Optional<CartItem> cartItem =
          cart.getCartItemList().stream()
              .filter(ci -> ci.getProduct().getId().equals(productId))
              .findFirst();

      if (cartItem.isPresent()) {
        cartItem.get().setAmount(cartItem.get().getAmount() + amount);
        final Cart updatedCart = calculateTotalPrice(cart);
        cart = cartRepository.save(updatedCart);
        return cart;
      }
    }

    final Product product = productService.findById(productId);
    final CartItem cartItem = CartItem.builder().cart(cart).amount(amount).product(product).build();

    cart.getCartItemList().add(cartItem);
    cart = cartRepository.save(calculateTotalPrice(cart));
    return cart;
  }

  @Override
  public Cart incrCartItem(final Long productId, final Integer amount) {
    Cart cart = getCart();

    final CartItem cartItem =
        cart.getCartItemList().stream()
            .filter(ci -> ci.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(format(EXCEPTION_MESSAGE, productId)));

    cartItem.setAmount(cartItem.getAmount() + amount);
    cart = cartRepository.save(calculateTotalPrice(cart));
    return cart;
  }

  @Override
  public Cart decrCartItem(final Long productId, final Integer amount) {
    Cart cart = getCart();

    final CartItem cartItem =
        cart.getCartItemList().stream()
            .filter(ci -> ci.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(format(EXCEPTION_MESSAGE, productId)));

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
  public Cart removeCartItem(final Long productId) {
    Cart cart = getCart();

    final CartItem cartItem =
        cart.getCartItemList().stream()
            .filter(ci -> ci.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(format(EXCEPTION_MESSAGE, productId)));

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
    final Cart cart = getCart();
    cart.getCartItemList().clear();
    cart.setTotalPrice(null);
    cartRepository.save(cart);
  }

  private Cart calculateTotalPrice(final Cart cart) {
    cart.setTotalPrice(ZERO);
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

  private Cart createCart(final Customer customer) {
    return Cart.builder().customer(customer).cartItemList(new ArrayList<>()).build();
  }
}
