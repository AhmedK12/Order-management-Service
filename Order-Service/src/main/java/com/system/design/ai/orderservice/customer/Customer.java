package com.system.design.ai.orderservice.customer;

import com.system.design.ai.orderservice.Auditable;
import com.system.design.ai.orderservice.address.Address;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.Pattern;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @NotNull
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "Mobile number must be valid")
    @NotBlank(message = "Mobile number is required")
    private String mobile;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    public Customer() {
        addresses = new ArrayList<>();
    }
}
