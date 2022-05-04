package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import com.switchfully.eurderproject.item_group.domain.ItemGroupRepository;
import com.switchfully.eurderproject.item_group.service.ItemGroupMapper;
import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import com.switchfully.eurderproject.order.domain.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ItemGroupRepository itemGroupRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;
    private final ItemGroupMapper itemGroupMapper;

    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository, ItemGroupRepository itemGroupRepository, ItemRepository itemRepository, OrderMapper orderMapper, ItemGroupMapper itemGroupMapper) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.itemGroupRepository = itemGroupRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
        this.itemGroupMapper = itemGroupMapper;
    }

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        List<ItemGroup> itemGroupList = itemGroupMapper.toItemGroup(createOrderDTO.getCreateItemGroupDTOList());
        itemGroupList.forEach(itemGroupRepository::save);
        orderItems(itemGroupList);
        Order order = new Order(customerRepository.getCustomerById(createOrderDTO.getCustomerId()).getId(), itemGroupList);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    private void orderItems(List<ItemGroup> itemGroupList) {
        for (ItemGroup itemGroup : itemGroupList) {
            Item orderedItem = itemRepository.getItemById(itemGroup.getItemId());
            if (orderedItem.getAmountAvailable() >= itemGroup.getAmount()) {
                orderedItem.changeAmountAvailable(orderedItem.getAmountAvailable() - itemGroup.getAmount());
                // OK TO THROW EXCEPTION?
                itemRepository.save(orderedItem);
            }
        }
    }
}
