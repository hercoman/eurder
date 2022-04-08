package com.switchfully.eurderproject.customer.domain;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {
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
        return customersById.get(customerId);
    }
}
