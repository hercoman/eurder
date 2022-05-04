package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        return new OrderDTO()
                .setId(order.getId())
                .setCustomerId(order.getCustomerId())
                .setTotalPrice(order.getTotalPrice());
    }
}
