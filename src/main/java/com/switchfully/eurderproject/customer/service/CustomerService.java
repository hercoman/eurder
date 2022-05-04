package com.switchfully.eurderproject.customer.service;

import com.switchfully.eurderproject.customer.api.dto.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.dto.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    private final Logger customerServiceLogger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
        assertEmailIsUnique(createCustomerDTO.getEmail());
        Customer customer = customerMapper.toCustomer(createCustomerDTO);
        customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    private void assertEmailIsUnique(String email) {
        if (customerRepository.findAll().stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email))) {
            customerServiceLogger.error("Attempted to create customer with already existing e-mail address");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to use given e-mail address for new customer's email address");
        }
        customerServiceLogger.info("Successfully validated new customer's email address to be unique");
    }

    public List<CustomerDTO> viewAll() {
        return customerMapper.toDTO(customerRepository.findAll());
    }

    public CustomerDTO viewCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            customerServiceLogger.error("No customer could be found for id " + customerId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find customer for id " + customerId);
        }
        return customerMapper.toDTO(customerRepository.getById(customerId));
    }
}
