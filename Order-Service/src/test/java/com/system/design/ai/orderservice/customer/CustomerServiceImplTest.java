//package com.system.design.ai.orderservice.customer;
//
//
//import com.system.design.ai.orderservice.order.Order;
//import com.system.design.ai.orderservice.order.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//        import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CustomerServiceImplTest {
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private CustomerMapper customerMapper;
//
//    @InjectMocks
//    private CustomerServiceImpl customerService;
//
//    private Customer customer;
//    private CustomerRequestDTO customerRequest;
//    private CustomerResponseDTO customerResponse;
//    private Order order;
//
//    @BeforeEach
//    void setUp() {
//        // Setup Customer
//        customer = new Customer();
//        customer.setId(1L);
//        customer.setFirstname("John");
//        customer.setLastname("Doe");
//        customer.setEmail("john.doe@example.com");
//        customer.setMobile("+1234567890");
//
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
//        // Setup Order
//        order = new Order();
//        order.setId(1L);
//        order.setCustomerId(1L);
//    }
//
//    @Test
//    void createCustomer_Success() {
//        // Arrange
//        when(customerMapper.toEntity(any(CustomerRequestDTO.class))).thenReturn(customer);
//        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
//        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerResponse);
//
//        // Act
//        CustomerResponseDTO result = customerService.createCustomer(customerRequest);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(customerResponse.getId(), result.getId());
//        assertEquals(customerResponse.getEmail(), result.getEmail());
//        verify(customerRepository, times(1)).save(any(Customer.class));
//    }
//
//    @Test
//    void getCustomer_Success() {
//        // Arrange
//        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
//        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerResponse);
//
//        // Act
//        CustomerResponseDTO result = customerService.getCustomer(1L);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(customerResponse.getId(), result.getId());
//        assertEquals(customerResponse.getEmail(), result.getEmail());
//    }
//
//    @Test
//    void getOrdersByCustomerId_Success() {
//        // Arrange
//        List<Order> orders = Arrays.asList(order);
//        when(orderRepository.findByCustomerId(anyLong())).thenReturn(orders);
//
//        // Act
//        List<Order> result = customerService.getOrdersByCustomerId(1L);
//
//        // Assert
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(1L, result.get(0).getCustomerId());
//    }
//
//    @Test
//    void getCustomer_NotFound() {
//        // Arrange
//        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(1L));
//    }
//}