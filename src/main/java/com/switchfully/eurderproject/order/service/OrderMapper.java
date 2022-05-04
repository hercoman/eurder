package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.item_group.service.ItemGroupMapper;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final ItemGroupMapper itemGroupMapper;

    public OrderMapper(ItemGroupMapper itemGroupMapper) {
        this.itemGroupMapper = itemGroupMapper;
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO()
                .setId(order.getId())
                .setCustomerId(order.getCustomerId())
                .setItemGroupDTOList(itemGroupMapper.toItemGroupDTO(order.getItemGroupList()))
                .setTotalPrice(order.getTotalPrice());
    }
}
