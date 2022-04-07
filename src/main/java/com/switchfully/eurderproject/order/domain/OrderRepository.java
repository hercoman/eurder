package com.switchfully.eurderproject.order.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Map<String, Order> ordersById;

    public OrderRepository() {
        this.ordersById = new HashMap<>();
    }


}
