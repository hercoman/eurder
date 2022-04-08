package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.api.dto.UpdatedCreateOrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final Logger orderRepositoryLogger = LoggerFactory.getLogger(OrderMapper.class);

    private final ItemRepository itemRepository;

    public OrderMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Order toOrder(UpdatedCreateOrderDTO updatedCreateOrderDTO) {
        return new Order(updatedCreateOrderDTO.getCustomerId(), updatedCreateOrderDTO.getItemGroupDTOList());
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO()
                .setId(order.getId())
                .setCustomerId(order.getCustomerId())
                .setItemGroupDTOList(order.getItemGroupDTOList());
    }
}
