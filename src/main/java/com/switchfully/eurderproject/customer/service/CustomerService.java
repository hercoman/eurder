package com.switchfully.eurderproject.customer.service;

import com.switchfully.eurderproject.customer.api.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
        Customer customer = customerMapper.toCustomer(createCustomerDTO);
        customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }
}
