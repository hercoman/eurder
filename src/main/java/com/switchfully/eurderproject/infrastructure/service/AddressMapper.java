package com.switchfully.eurderproject.infrastructure.service;

import com.switchfully.eurderproject.infrastructure.api.dto.AddressDTO;
import com.switchfully.eurderproject.infrastructure.domain.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    private final PostalCodeMapper postalCodeMapper;

    public AddressMapper(PostalCodeMapper postalCodeMapper) {
        this.postalCodeMapper = postalCodeMapper;
    }

    public Address toAddress(AddressDTO address) {
        return new Address(
                address.getStreetName(),
                address.getStreetNumber(),
                postalCodeMapper.toPostalCode(address.getPostalCode()));
    }

    public AddressDTO toDTO(Address address) {
        return new AddressDTO(
                address.getStreetName(),
                address.getStreetNumber(),
                postalCodeMapper.toDTO(address.getPostalCode()));
    }
}
