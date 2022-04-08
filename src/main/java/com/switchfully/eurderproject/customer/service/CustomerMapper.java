package com.switchfully.eurderproject.customer.service;

import com.switchfully.eurderproject.customer.api.dto.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.dto.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    public Customer toCustomer(CreateCustomerDTO createCustomerDTO) {
        return new Customer(
                createCustomerDTO.getFirstName(),
                createCustomerDTO.getLastName(),
                createCustomerDTO.getEmail(),
                createCustomerDTO.getAddress(),
                createCustomerDTO.getPhoneNumber());
    }

    public CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO()
                .setId(customer.getId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setEmail(customer.getEmail())
                .setAddress(customer.getAddress())
                .setPhoneNumber(customer.getPhoneNumber());
    }

    public List<CustomerDTO> toDTO(Collection<Customer> customerCollection) {
        return customerCollection.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
