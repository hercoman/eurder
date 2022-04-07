package com.switchfully.eurderproject.customer.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
}
