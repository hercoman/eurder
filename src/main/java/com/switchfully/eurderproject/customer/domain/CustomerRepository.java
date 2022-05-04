package com.switchfully.eurderproject.customer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerRepository {
    private final Logger customerRepositoryLogger = LoggerFactory.getLogger(CustomerRepository.class);

    private final Map<String, Customer> customersById;

    public CustomerRepository() {
        this.customersById = new HashMap<>();
    }

    public void save(Customer customer) {
        customersById.put(customer.getId(), customer);
    }

    public boolean isEmailUnique(String email) {
        return customersById.values().stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email));
    }

    public Collection<Customer> getAll() {
        return customersById.values();
    }

    public Customer getCustomerById(String customerId) {
        Customer foundCustomer = customersById.get(customerId);
        if(foundCustomer == null){
            customerRepositoryLogger.error("No customer could be found for id " + customerId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find customer for id " + customerId);
        }
        return foundCustomer ;
    }
}
