package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item_group.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import com.switchfully.eurderproject.item_group.domain.ItemGroupRepository;
import com.switchfully.eurderproject.item_group.service.ItemGroupMapper;
import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import com.switchfully.eurderproject.order.domain.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final Logger orderServiceLogger = LoggerFactory.getLogger(OrderService.class);

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
        assertCustomerExists(createOrderDTO.getCustomerId());
        assertItemsExist(createOrderDTO.getCreateItemGroupDTOList());
        List<ItemGroup> itemGroupList = itemGroupMapper.toItemGroup(createOrderDTO.getCreateItemGroupDTOList());
        itemGroupList.forEach(itemGroupRepository::save);
        orderItems(itemGroupList);
        Order order = new Order(customerRepository.getById(createOrderDTO.getCustomerId()).getId(), itemGroupList);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    private void assertCustomerExists(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            orderServiceLogger.error("No customer could be found for id " + customerId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find customer for id " + customerId);
        }
    }

    private void assertItemsExist(List<CreateItemGroupDTO> createItemGroupDTOList) {
        for (CreateItemGroupDTO item : createItemGroupDTOList) {
            String createItemId = item.getItemId();
            if (!itemRepository.existsById(createItemId)) {
                orderServiceLogger.error("No item could be found for id " + createItemId);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No item found for id " + createItemId);
            }
        }
    }

    private void orderItems(List<ItemGroup> itemGroupList) {
        for (ItemGroup itemGroup : itemGroupList) {
            Item orderedItem = itemRepository.getById(itemGroup.getItemId());
            if (orderedItem.getAmountAvailable() >= itemGroup.getAmount()) {
                orderedItem.changeAmountAvailable(orderedItem.getAmountAvailable() - itemGroup.getAmount());
                // OK TO THROW EXCEPTION?
                itemRepository.save(orderedItem);
            }
        }
    }
}
