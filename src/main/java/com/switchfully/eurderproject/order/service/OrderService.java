package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.order.api.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import com.switchfully.eurderproject.order.domain.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        Order order = orderMapper.toOrder(createOrderDTO);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }
}
