package com.switchfully.eurderproject.customer.service;

import com.switchfully.eurderproject.customer.api.dto.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.dto.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import org.springframework.stereotype.Component;

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
}
