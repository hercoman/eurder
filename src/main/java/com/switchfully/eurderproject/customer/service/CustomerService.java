package com.switchfully.eurderproject.customer.service;

import com.switchfully.eurderproject.customer.api.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerService {
    private final Logger serviceLogger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
        boolean emailExists = customerRepository.assertUniqueEmailAddress(createCustomerDTO.getEmail());
        validateAndCheckLoggingMessage(emailExists);
        Customer customer = customerMapper.toCustomer(createCustomerDTO);
        customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    private void validateAndCheckLoggingMessage(boolean condition) {
        if (condition) {
            serviceLogger.error("Attempted to create customer with already existing e-mail address");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to use given e-mail address for new customer's email address");
        }
        serviceLogger.info("Successfully validated new customer's email address to be unique");
    }
}
