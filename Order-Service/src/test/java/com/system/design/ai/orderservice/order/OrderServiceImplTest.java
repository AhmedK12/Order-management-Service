package com.system.design.ai.orderservice.order;

import com.system.design.ai.orderservice.customer.Customer;
import com.system.design.ai.orderservice.customer.CustomerRepository;
import com.system.design.ai.orderservice.exception.ResourceNotFoundException;
import com.system.design.ai.orderservice.orderitem.OrderItem;
import com.system.design.ai.orderservice.orderitem.OrderItemMapper;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Nested
    @DisplayName("createOrder Tests")
    class CreateOrderTests {

        @Test
        @DisplayName("Should create order successfully when valid request")
        void shouldCreateOrderSuccessfully() {
            // Arrange
            OrderRequestDTO request = new OrderRequestDTO(1L, OrderStatus.PENDING, 0, null, Collections.emptyList());
            Order mockOrder = new Order();
            OrderResponseDTO expectedResponse = new OrderResponseDTO(1L, 1L, LocalDateTime.now(), OrderStatus.PENDING, 0, null, Collections.emptyList());

            when(customerRepository.findById(1L)).thenReturn(Optional.of(mock(Customer.class)));
            when(orderMapper.toEntity(request)).thenReturn(mockOrder);
            when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
            when(orderMapper.toResponseDTO(mockOrder)).thenReturn(expectedResponse);

            // Act
            OrderResponseDTO result = orderService.createOrder(request);

            // Assert
            assertNotNull(result);
            assertEquals(expectedResponse, result);
            verify(orderRepository).save(any(Order.class));
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when customer not found")
        void shouldThrowExceptionWhenCustomerNotFound() {
            // Arrange
            OrderRequestDTO request = new OrderRequestDTO(1L, OrderStatus.PENDING, 0, null, Collections.emptyList());
            when(customerRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(request));
            verify(orderRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("updateOrderStatus Tests")
    class UpdateOrderStatusTests {

        @Test
        @DisplayName("Should update order status successfully")
        void shouldUpdateOrderStatusSuccessfully() {
            // Arrange
            Long orderId = 1L;
            Order existingOrder = new Order();
            Order updatedOrder = new Order();
            OrderResponseDTO expectedResponse = new OrderResponseDTO(orderId, 1L, LocalDateTime.now(), OrderStatus.COMPLETED, 0, null, Collections.emptyList());

            when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
            when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);
            when(orderMapper.toResponseDTO(updatedOrder)).thenReturn(expectedResponse);

            // Act
            OrderResponseDTO result = orderService.updateOrderStatus(orderId, OrderStatus.COMPLETED);

            // Assert
            assertNotNull(result);
            assertEquals(expectedResponse, result);
            verify(orderRepository).save(any(Order.class));
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when order not found")
        void shouldThrowExceptionWhenOrderNotFound() {
            // Arrange
            when(orderRepository.findById(any())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class,
                    () -> orderService.updateOrderStatus(1L, OrderStatus.COMPLETED));
        }

        @Test
        @DisplayName("Should throw OptimisticLockException when concurrent update occurs")
        void shouldThrowOptimisticLockExceptionOnConcurrentUpdate() {
            // Arrange
            Long orderId = 1L;
            Order existingOrder = new Order();
            when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
            when(orderRepository.save(any(Order.class))).thenThrow(OptimisticLockException.class);

            // Act & Assert
            assertThrows(OptimisticLockException.class,
                    () -> orderService.updateOrderStatus(orderId, OrderStatus.COMPLETED));
        }
    }

    @Nested
    @DisplayName("calculateTotalOrderValue Tests")
    class CalculateTotalOrderValueTests {

        @Test
        @DisplayName("Should calculate total order value correctly")
        void shouldCalculateTotalOrderValueCorrectly() {
            // Arrange
            Long customerId = 1L;
            OrderItem item1 = new OrderItem();
            item1.setPrice(new BigDecimal("10.00"));
            item1.setQuantity(2);

            OrderItem item2 = new OrderItem();
            item2.setPrice(new BigDecimal("20.00"));
            item2.setQuantity(1);

            Order order = new Order();
            order.setItems(Arrays.asList(item1, item2));

            when(orderRepository.findAllByCustomerId(customerId))
                    .thenReturn(Collections.singletonList(order));

            // Act
            BigDecimal result = orderService.calculateTotalOrderValue(customerId);

            // Assert
            assertEquals(new BigDecimal("40.00"), result);
        }

        @Test
        @DisplayName("Should return zero when customer has no orders")
        void shouldReturnZeroWhenNoOrders() {
            // Arrange
            when(orderRepository.findAllByCustomerId(any()))
                    .thenReturn(Collections.emptyList());

            // Act
            BigDecimal result = orderService.calculateTotalOrderValue(1L);

            // Assert
            assertEquals(BigDecimal.ZERO, result);
        }
    }

    @Nested
    @DisplayName("getAllOrders Tests")
    class GetAllOrdersTests {

        @Test
        @DisplayName("Should return all orders for customer")
        void shouldReturnAllOrdersForCustomer() {
            // Arrange
            Long customerId = 1L;
            Order order = new Order();
            OrderResponseDTO responseDTO = new OrderResponseDTO(1L, 1L, LocalDateTime.now(), OrderStatus.COMPLETED, 0, null, Collections.emptyList());

            when(orderRepository.findAllByCustomerId(customerId))
                    .thenReturn(Collections.singletonList(order));
            when(orderMapper.toResponseDTO(order)).thenReturn(responseDTO);

            // Act
            List<OrderResponseDTO> result = orderService.getAllOrders(customerId);

            // Assert
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(responseDTO, result.get(0));
        }

        @Test
        @DisplayName("Should return empty list when customer has no orders")
        void shouldReturnEmptyListWhenNoOrders() {
            // Arrange
            when(orderRepository.findAllByCustomerId(any()))
                    .thenReturn(Collections.emptyList());

            // Act
            List<OrderResponseDTO> result = orderService.getAllOrders(1L);

            // Assert
            assertTrue(result.isEmpty());
        }
    }
}
