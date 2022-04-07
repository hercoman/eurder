package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.order.api.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toOrder(CreateOrderDTO createOrderDTO) {
        return new Order(createOrderDTO.getCustomerId(), createOrderDTO.getItemGroupList());
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO()
                .setId(order.getId())
                .setCustomerId(order.getCustomerId())
                .setItemGroupList(order.getItemGroupList());
    }
}
