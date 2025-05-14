package com.system.design.ai.orderservice.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        logger.info("Received request to create order for customer ID: {}", orderRequestDTO.customerId());

        OrderResponseDTO response = orderService.createOrder(orderRequestDTO);

        logger.info("Order created with ID: {}", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status ) {
        logger.info("Received request to update order ID: {} to status: {}", id, status);

        OrderResponseDTO response = orderService.updateOrderStatus(id, status);

        logger.info("Order ID: {} status updated to: {}", id, response.status());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/all-order")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@PathVariable Long customerId) {
        logger.info("Fetching all orders for customer ID: {}", customerId);

        List<OrderResponseDTO> orders = orderService.getAllOrders(customerId);

        logger.debug("Fetched {} orders for customer ID: {}", orders.size(), customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{customerId}/total-value")
    public ResponseEntity<BigDecimal> getTotalOrderValue(@PathVariable Long customerId) {
        logger.info("Calculating total order value for customer ID: {}", customerId);

        BigDecimal total = orderService.calculateTotalOrderValue(customerId);

        logger.debug("Total order value for customer ID {} is {}", customerId, total);
        return ResponseEntity.ok(total);
    }
}
