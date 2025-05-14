package com.system.design.ai.orderservice.order;

import com.system.design.ai.orderservice.customer.CustomerRepository;
import com.system.design.ai.orderservice.exception.ResourceNotFoundException;
import com.system.design.ai.orderservice.orderitem.OrderItem;
import com.system.design.ai.orderservice.orderitem.OrderItemMapper;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        logger.info("Creating new order for customerId: {}", orderRequest.customerId());
        if(customerRepository.findById(orderRequest.customerId()).isEmpty()) {
            throw new ResourceNotFoundException("Customer not found");
        }
        Order order = orderMapper.toEntity(orderRequest);
        if (order.getItems() != null) {
            Optional.of(order.getItems())
                    .ifPresent(items -> items.forEach(item -> item.setOrder(order)));

        }
        Order savedOrder = orderRepository.save(order);
        logger.debug("Order saved with ID: {} and {} items", savedOrder.getId(), order.getItems().size());
        return orderMapper.toResponseDTO(savedOrder);
    }

    @Transactional
    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status) {
        logger.info("Updating order status. OrderId: {}, NewStatus: {}", orderId, status);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    logger.error("Order not found for id: {}", orderId);
                    return new ResourceNotFoundException("Order is not exit");
                });

        order.setStatus(status);
        Order updatedOrder = null;
        try {
             updatedOrder = orderRepository.save(order);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("The order was updated by another user, please reload the order and try again.");
        }

        logger.debug("Order ID {} status updated to {}", orderId, status);
        return orderMapper.toResponseDTO(updatedOrder);
    }

    @Override
    public BigDecimal calculateTotalOrderValue(Long customerId) {
        logger.info("Calculating total order value for customerId: {}", customerId);

        List<Order> orders = orderRepository.findAllByCustomerId(customerId);

        BigDecimal total = orders.stream()
                .flatMap(order -> Optional.ofNullable(order.getItems()).orElse(List.of()).stream())
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        logger.debug("Total value calculated for customerId {}: {}", customerId, total);
        return total;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(Long customerId) {
        logger.info("Fetching all orders for customerId: {}", customerId);

        List<OrderResponseDTO> orders = orderRepository.findAllByCustomerId(customerId)
                .stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());

        logger.debug("Fetched {} orders for customerId {}", orders.size(), customerId);
        return orders;
    }
}
