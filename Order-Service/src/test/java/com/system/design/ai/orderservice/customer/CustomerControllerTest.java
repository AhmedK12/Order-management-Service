//package com.system.design.ai.orderservice.customer;
//
//import com.system.design.ai.orderservice.order.Order;
//import com.system.design.ai.orderservice.order.OrderStatus;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class CustomerControllerTest {
//
//    @Mock
//    private CustomerService customerService;
//
//    @InjectMocks
//    private CustomerController customerController;
//
//    private CustomerRequestDTO customerRequest;
//    private CustomerResponseDTO customerResponse;
//    private Order sampleOrder;
//
//    @BeforeEach
//    void setUp() {
//        // Setup CustomerRequestDTO
//        customerRequest = new CustomerRequestDTO();
//        customerRequest.setFirstname("John");
//        customerRequest.setLastname("Doe");
//        customerRequest.setEmail("john.doe@example.com");
//        customerRequest.setMobile("+1234567890");
//
//        // Setup CustomerResponseDTO
//        customerResponse = new CustomerResponseDTO();
//        customerResponse.setId(1L);
//        customerResponse.setFirstname("John");
//        customerResponse.setLastname("Doe");
//        customerResponse.setEmail("john.doe@example.com");
//        customerResponse.setMobile("+1234567890");
//
//        // Setup sample Order
//        sampleOrder = new Order();
//        sampleOrder.setId(1L);
//        sampleOrder.setCustomerId(1L);
//        sampleOrder.setOrderDate(LocalDateTime.now());
//        sampleOrder.setStatus(OrderStatus.PENDING);
//    }
//
//    @Test
//    void createCustomer_Success() {
//        // Arrange
//        when(customerService.createCustomer(any(CustomerRequestDTO.class)))
//                .thenReturn(customerResponse);
//
//        // Act
//        ResponseEntity<CustomerResponseDTO> response = customerController.createCustomer(customerRequest);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(customerResponse, response.getBody());
//        assertEquals("John", response.getBody().getFirstname());
//        assertEquals("Doe", response.getBody().getLastname());
//    }
//
//    @Test
//    void getCustomer_Success() {
//        // Arrange
//        when(customerService.getCustomer(anyLong()))
//                .thenReturn(customerResponse);
//
//        // Act
//        ResponseEntity<CustomerResponseDTO> response = customerController.getCustomer(1L);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(customerResponse, response.getBody());
//        assertEquals(1L, response.getBody().getId());
//    }
//
//    @Test
//    void getCustomerOrders_Success() {
//        // Arrange
//        List<Order> orders = Arrays.asList(sampleOrder);
//        when(customerService.getOrdersByCustomerId(anyLong()))
//                .thenReturn(orders);
//
//        // Act
//        ResponseEntity<List<Order>> response = customerController.getCustomerOrders(1L);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//        assertEquals(1L, response.getBody().get(0).getCustomerId());
//    }
//}