package com.system.design.ai.orderservice.customer;

import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "Mobile number must be valid")
    @NotBlank(message = "Mobile number is required")
    private String mobile;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @Size(min = 1, message = "At least one address is required")
    private List<Address> addresses;
}
