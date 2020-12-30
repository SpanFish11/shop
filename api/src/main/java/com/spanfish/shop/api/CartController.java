package com.spanfish.shop.api;

import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.CartMapper;
import com.spanfish.shop.model.dto.CartDTO;
import com.spanfish.shop.model.entity.Cart;
import com.spanfish.shop.model.request.cart.CartItemRequest;
import com.spanfish.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
  private final CartMapper mapper;

  @Operation(summary = "Get cart", description = "Endpoint for getting cart with all items")
  @GetMapping
  public ResponseEntity<CartDTO> getCart() {
    final Cart cart = cartService.getCart();
    return ok(mapper.toDTO(cart));
  }

  @Operation(summary = "Add product to cart", description = "End point for adding item to cart")
  @PostMapping
  public ResponseEntity<CartDTO> addItemToCart(@RequestBody @Valid final CartItemRequest request) {
    final Cart cart = cartService.addToCart(request.getProductId(), request.getAmount());
    return ok(mapper.toDTO(cart));
  }

  @Operation(summary = "Increment quantity", description = "Endpoint to increase item quantity")
  @PostMapping("/incr")
  public ResponseEntity<CartDTO> incrCartItem(@RequestBody @Valid final CartItemRequest request) {
    final Cart cart = cartService.incrCartItem(request.getProductId(), request.getAmount());
    return ok(mapper.toDTO(cart));
  }

  @Operation(summary = "Decrement quantity", description = "Endpoint to decrement item quantity")
  @PostMapping("/decr")
  public ResponseEntity<CartDTO> decrCartItem(@RequestBody @Valid final CartItemRequest request) {
    final Cart cart = cartService.decrCartItem(request.getProductId(), request.getAmount());
    return ok(mapper.toDTO(cart));
  }

  @Operation(summary = "Remove item", description = "Endpoint for removing an item from сart")
  @DeleteMapping("/items/{item_id}")
  public ResponseEntity<CartDTO> deleteCartItem(
      @PathVariable("item_id") @Min(1) @NotNull final Long id) {
    final Cart cart = cartService.removeCartItem(id);
    return ok(mapper.toDTO(cart));
  }

  @Operation(summary = "Clear cart", description = "Endpoint for cleaning cart items")
  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteCartItems() {
    cartService.clearCart();
    return ok().build();
  }
}
