package com.switchfully.eurderproject.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
