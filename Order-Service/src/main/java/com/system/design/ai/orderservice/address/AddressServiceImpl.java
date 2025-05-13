package com.system.design.ai.orderservice.address;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository repository, AddressMapper addressMapper) {
        this.repository = repository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressResponseDTO create(AddressRequestDTO dto) {

        Address saved = repository.save(addressMapper.toEntity(dto));
        return addressMapper.toResponseDTO(saved);
    }

    @Override
    public List<AddressResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(addressMapper::toResponseDTO)
                .toList();
    }
}

