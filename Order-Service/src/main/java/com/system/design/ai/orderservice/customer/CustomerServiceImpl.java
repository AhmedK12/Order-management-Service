package com.system.design.ai.orderservice.customer;

import com.system.design.ai.orderservice.address.AddressMapper;
import com.system.design.ai.orderservice.exception.ResourceNotFoundException;
import com.system.design.ai.orderservice.order.Order;
import com.system.design.ai.orderservice.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    public CustomerServiceImpl(CustomerRepository customerRepo, OrderRepository orderRepo,
                               CustomerMapper customerMapper, AddressMapper addressMapper) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO) {
        logger.info("Creating customer with email: {}", customerDTO.email());

        Customer newCustomer = new Customer();
        newCustomer.setFirstname(customerDTO.firstname());
        newCustomer.setLastname(customerDTO.lastname());
        newCustomer.setEmail(customerDTO.email());
        newCustomer.setMobile(customerDTO.mobile());

        if (customerDTO.address() != null) {
            newCustomer.setAddresses(customerDTO.address().stream()
                    .map(addressMapper::toEntity)
                    .toList());
            logger.debug("Customer has {} addresses", customerDTO.address().size());
        }

        Customer saved = customerRepo.save(newCustomer);
        logger.info("Customer created with ID: {}", saved.getId());

        return customerMapper.toResponseDTO(saved);
    }

    @Override
    public CustomerResponseDTO getCustomer(Long id) {
        logger.info("Fetching customer with ID: {}", id);

        Customer customer = customerRepo.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> {
                    logger.error("Customer not found with ID: {}", id);
                    return new ResourceNotFoundException("Customer not found");
                });

        logger.debug("Customer fetched: {}", customer.getEmail());
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        logger.info("Fetching orders for customer ID: {}", customerId);

        List<Order> orders = orderRepo.findAllByCustomerId(customerId);
        logger.debug("Fetched {} orders for customer ID: {}", orders.size(), customerId);

        return orders;
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepo.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        customer.setDeleted(true);
        customerRepo.save(customer);
    }

}
