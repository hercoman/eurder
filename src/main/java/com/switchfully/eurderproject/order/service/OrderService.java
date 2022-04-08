package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.item.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemGroup;
import com.switchfully.eurderproject.item.domain.ItemGroupRepository;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item.service.ItemMapper;
import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.api.dto.UpdatedCreateOrderDTO;
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
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    public OrderService(OrderRepository orderRepository, ItemGroupRepository itemGroupRepository, ItemRepository itemRepository, OrderMapper orderMapper, ItemMapper itemMapper) {
        this.orderRepository = orderRepository;
        this.itemGroupRepository = itemGroupRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
    }

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        List<ItemGroupDTO> itemGroupDTOList = createItemGroupsFromOrder(createOrderDTO);
        updateItemInventory(itemGroupDTOList);
        UpdatedCreateOrderDTO updatedCreateOrderDTO = new UpdatedCreateOrderDTO()
                .setCustomerId(createOrderDTO.getCustomerId())
                .setItemGroupDTOList(itemGroupDTOList);
        Order order = orderMapper.toOrder(updatedCreateOrderDTO);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    private void updateItemInventory(List<ItemGroupDTO> itemGroupDTOList) {
        for (ItemGroupDTO itemGroupDTO : itemGroupDTOList) {
            Item orderedItem = itemRepository.getItemById(itemGroupDTO.getItemId());
            if (orderedItem.getAmountAvailable() > itemGroupDTO.getAmount()) {
                orderedItem.changeAmountAvailable(orderedItem.getAmountAvailable() - itemGroupDTO.getAmount());
                itemRepository.save(orderedItem);
            }
        }
    }

    private List<ItemGroupDTO> createItemGroupsFromOrder(CreateOrderDTO createOrderDTO) {
        List<CreateItemGroupDTO> createItemGroupDTOList = createOrderDTO.getCreateItemGroupDTOList();
        List<ItemGroup> savedItemGroupList = saveItemGroupsInRepository(createItemGroupDTOList);
        return itemMapper.toItemGroupDTO(savedItemGroupList);
    }

    private List<ItemGroup> saveItemGroupsInRepository(List<CreateItemGroupDTO> createItemGroupDTOList) {
        return itemMapper.toItemGroup(createItemGroupDTOList).stream()
                .map(itemGroupRepository::save).toList();
    }
}
