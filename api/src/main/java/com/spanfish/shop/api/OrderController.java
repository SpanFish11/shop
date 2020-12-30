package com.spanfish.shop.api;

import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.OrderMapper;
import com.spanfish.shop.model.dto.OrderDTO;
import com.spanfish.shop.model.request.opder.UpdateStatusRequest;
import com.spanfish.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final OrderMapper mapper;

  @Operation(summary = "Make order", description = "Endpoint for order creation")
  @PostMapping
  public ResponseEntity<OrderDTO> makeOrder() {
    return ok(mapper.toDTO(orderService.saveOrder()));
  }

  @Operation(summary = "Order list", description = "Endpoint to get a list of all customer orders")
  @GetMapping
  public ResponseEntity<Page<OrderDTO>> getAllCustomerOrders(
      @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0)
          final Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Min(5)
          final Integer pageSize) {
    final Page<OrderDTO> orders =
        orderService.findAllCustomerOrders(page, pageSize).map(mapper::toDTO);
    return ok(orders);
  }

  @Operation(
      summary = "Number of orders",
      description = "Endpoint to get the number of orders from the customer")
  @GetMapping("/count")
  public ResponseEntity<Integer> getAllOrdersCount() {
    final Integer orderCount = orderService.getAllOrdersCount();
    return ok(orderCount);
  }

  @Operation(
      summary = "List of all orders",
      description =
          "Endpoint for receiving all orders made by customers with the ability to search by order code")
  @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
  @GetMapping("/all")
  public ResponseEntity<Page<OrderDTO>> getAllOrders(
      @RequestParam(value = "query", required = false) final String number,
      @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0)
          final Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Min(5)
          final Integer pageSize) {
    final Page<OrderDTO> orders =
        orderService.findAllOrders(number, page, pageSize).map(mapper::toDTO);
    return ok(orders);
  }

  @Operation(
      summary = "Change order status",
      description = "Endpoint for changing the customer's order status")
  @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
  @PutMapping("/all")
  public ResponseEntity<HttpStatus> changeStatus(
      @RequestBody @Valid final UpdateStatusRequest request) {
    orderService.changeOrderStatus(request.getCode(), request.getStatus());
    return ok().build();
  }
}
