package com.system.design.ai.orderservice.address;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@Valid @RequestBody AddressRequestDTO dto) {
        return ResponseEntity.ok(addressService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAll() {
        return ResponseEntity.ok(addressService.findAll());
    }
}

