package com.switchfully.eurderproject.customer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {
    private final Logger customerRepositoryLogger = LoggerFactory.getLogger(CustomerRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Customer customer) {
        entityManager.persist(customer);
    }

    public boolean isEmailUnique(String email) {
        return getAll().stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email));
    }

    public Collection<Customer> getAll() {
        return entityManager.createQuery("select c from Customer c", Customer.class)
                .getResultList();
    }

    public Customer getCustomerById(String customerId) {
        List<Customer> foundCustomers = entityManager.createQuery("select c from Customer c where c.id = :id", Customer.class)
                .setParameter("id", customerId)
                .getResultList();
        if (foundCustomers.size() == 0) {
            customerRepositoryLogger.error("No customer could be found for id " + customerId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find customer for id " + customerId);
        }
        return foundCustomers.get(0);
    }
}
