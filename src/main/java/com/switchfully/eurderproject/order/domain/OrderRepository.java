package com.switchfully.eurderproject.order.domain;

import com.switchfully.eurderproject.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("select o from Order o where o.customer = :customer")
    List<Order> findByCustomer(@Param("customer") Customer customer);
}
