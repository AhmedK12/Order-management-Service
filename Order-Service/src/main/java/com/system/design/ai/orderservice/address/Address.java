package com.system.design.ai.orderservice.address;

import com.system.design.ai.orderservice.Auditable;
import com.system.design.ai.orderservice.customer.Customer;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    private String zip;

    @NotBlank(message = "Country is required")
    private String country;

    private String landmark;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
