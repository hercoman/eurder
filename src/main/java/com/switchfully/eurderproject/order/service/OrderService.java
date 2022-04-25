package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import com.switchfully.eurderproject.item.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemGroup;
import com.switchfully.eurderproject.item.domain.ItemGroupRepository;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item.service.ItemMapper;
import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import com.switchfully.eurderproject.order.domain.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final Logger orderServiceLogger = LoggerFactory.getLogger(OrderService.class);

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ItemGroupRepository itemGroupRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository, ItemGroupRepository itemGroupRepository, ItemRepository itemRepository, OrderMapper orderMapper, ItemMapper itemMapper) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.itemGroupRepository = itemGroupRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
    }

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        List<ItemGroupDTO> itemGroupList = createItemGroupsFromOrder(createOrderDTO.getCreateItemGroupDTOList());
        orderItems(itemGroupList);
//        UpdatedCreateOrderDTO updatedCreateOrderDTO = new UpdatedCreateOrderDTO()
//                .setCustomerId(foundCustomer.getId())
//                .setItemGroupDTOList(itemGroupList);
        Order order = new Order(customerRepository.getCustomerById(createOrderDTO.getCustomerId()).getId(), itemGroupList);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    private void orderItems(List<ItemGroupDTO> itemGroupDTOList) {
        for (ItemGroupDTO itemGroupDTO : itemGroupDTOList) {
            Item orderedItem = itemRepository.getItemById(itemGroupDTO.getItemId());
            if (orderedItem.getAmountAvailable() >= itemGroupDTO.getAmount()) {
                orderedItem.changeAmountAvailable(orderedItem.getAmountAvailable() - itemGroupDTO.getAmount());
                // OK TO THROW EXCEPTION?
                itemRepository.save(orderedItem);
            }
        }
    }

    private List<ItemGroupDTO> createItemGroupsFromOrder(List<CreateItemGroupDTO> createItemGroupDTOList1) {
        List<ItemGroup> savedItemGroupList = saveItemGroupsInRepository(createItemGroupDTOList1);
        return itemMapper.toItemGroupDTO(savedItemGroupList);
    }

    private List<ItemGroup> saveItemGroupsInRepository(List<CreateItemGroupDTO> createItemGroupDTOList) {
        return itemMapper.toItemGroup(createItemGroupDTOList).stream()
                .map(itemGroupRepository::save).toList();
    }
}
