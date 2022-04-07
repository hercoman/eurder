package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.ItemGroup;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.order.api.OrderDTO;
import com.switchfully.eurderproject.order.api.SavedOrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderMapper {
    private final ItemRepository itemRepository;

    public OrderMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Order toOrder(SavedOrderDTO savedOrderDTO) {
        return new Order(savedOrderDTO.getCustomerId(), savedOrderDTO.getItemGroupList());
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO()
                .setId(order.getId())
                .setCustomerId(order.getCustomerId())
                .setItemGroupList(order.getItemGroupList());
    }
}
