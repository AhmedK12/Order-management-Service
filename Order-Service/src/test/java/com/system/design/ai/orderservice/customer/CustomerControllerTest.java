package com.system.design.ai.orderservice.customer;

import com.system.design.ai.orderservice.order.Order;
import com.system.design.ai.orderservice.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerRequestDTO customerRequest;
    private CustomerResponseDTO customerResponse;
    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        // Setup CustomerRequestDTO
        customerResponse = new CustomerResponseDTO(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "+1234567890",
                List.of(),  // assuming no address in test
                LocalDateTime.now()
        );

        // Setup CustomerResponseDTO (assuming it's a record, you must use constructor)
        customerResponse = new CustomerResponseDTO(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "+1234567890",
                Collections.emptyList(),
                LocalDateTime.now()
        );

        // Setup sample Order
        sampleOrder = new Order();
        sampleOrder.setId(1L);
        sampleOrder.setCustomerId(1L);
        sampleOrder.setOrderDate(LocalDateTime.now());
        sampleOrder.setStatus(OrderStatus.PENDING);
    }

    @Test
    void createCustomer_Success() {
        // Arrange
        when(customerService.createCustomer(any(CustomerRequestDTO.class)))
                .thenReturn(customerResponse);

        // Act
        ResponseEntity<CustomerResponseDTO> response = customerController.createCustomer(customerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
        assertEquals("John", response.getBody().firstname());
        assertEquals("Doe", response.getBody().lastname());
    }

    @Test
    void getCustomer_Success() {
        // Arrange
        when(customerService.getCustomer(anyLong()))
                .thenReturn(customerResponse);

        // Act
        ResponseEntity<CustomerResponseDTO> response = customerController.getCustomer(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    void getCustomerOrders_Success() {
        // Arrange
        List<Order> orders = List.of(sampleOrder);
        when(customerService.getOrdersByCustomerId(anyLong()))
                .thenReturn(orders);

        // Act
        ResponseEntity<List<Order>> response = customerController.getCustomerOrders(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getCustomerId());
    }
}
