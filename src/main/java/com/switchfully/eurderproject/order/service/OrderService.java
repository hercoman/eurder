package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.item.api.CreateItemGroupDTO;
import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.ItemGroup;
import com.switchfully.eurderproject.item.domain.ItemGroupRepository;
import com.switchfully.eurderproject.item.service.ItemMapper;
import com.switchfully.eurderproject.order.api.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.OrderDTO;
import com.switchfully.eurderproject.order.api.SavedOrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import com.switchfully.eurderproject.order.domain.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final Logger orderServiceLogger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ItemGroupRepository itemGroupRepository;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    public OrderService(OrderRepository orderRepository, ItemGroupRepository itemGroupRepository, OrderMapper orderMapper, ItemMapper itemMapper) {
        this.orderRepository = orderRepository;
        this.itemGroupRepository = itemGroupRepository;
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
    }

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        List<CreateItemGroupDTO> createItemGroupDTOList = createOrderDTO.getItemGroupDTOList();
        List<ItemGroup> itemGroupList = itemMapper.toItemGroup(createItemGroupDTOList);
        List<ItemGroup> savedItemGroupList = itemGroupList.stream()
                .map(itemGroupRepository::save).toList();
        List<ItemGroupDTO> itemGroupDTOList = itemMapper.toItemGroupDTO(savedItemGroupList);
        SavedOrderDTO savedOrderDTO = new SavedOrderDTO()
                .setCustomerId(createOrderDTO.getCustomerId())
                .setItemGroupDTOList(itemGroupDTOList);
        Order order = orderMapper.toOrder(savedOrderDTO);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }
}
