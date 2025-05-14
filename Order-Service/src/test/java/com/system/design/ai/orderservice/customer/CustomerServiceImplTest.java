package com.system.design.ai.orderservice.customer;

import com.system.design.ai.orderservice.exception.ResourceNotFoundException;
import com.system.design.ai.orderservice.order.Order;
import com.system.design.ai.orderservice.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerRequestDTO customerRequest;
    private CustomerResponseDTO customerResponse;
    private Order order;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("John");
        customer.setLastname("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setMobile("+1234567890");

        customerRequest = new CustomerRequestDTO("John", "Doe", "john.doe@example.com", "+1234567890",null);

        customerResponse = new CustomerResponseDTO(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "+1234567890",
                List.of(), // Assuming no address for now
                LocalDateTime.now() // Or a fixed time if you want deterministic tests
        );

        order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
    }


    @Test
    void createCustomer_Success() {
        when(customerMapper.toEntity(any(CustomerRequestDTO.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toResponseDTO(any(Customer.class))).thenReturn(customerResponse);

        CustomerResponseDTO result = customerService.createCustomer(customerRequest);

        assertNotNull(result);
        assertEquals(customerResponse.id(), result.id());
        assertEquals(customerResponse.email(), result.email());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void getCustomer_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toResponseDTO(customer)).thenReturn(customerResponse);

        CustomerResponseDTO result = customerService.getCustomer(1L);

        assertNotNull(result);
        assertEquals(customerResponse.id(), result.id());
        assertEquals(customerResponse.email(), result.email());
    }

    @Test
    void getOrdersByCustomerId_Success() {
        when(orderRepository.findAllByCustomerId(1L)).thenReturn(Collections.singletonList(order));

        List<Order> result = customerService.getOrdersByCustomerId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getCustomerId());
    }

    @Test
    void getCustomer_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomer(1L));
    }
}
