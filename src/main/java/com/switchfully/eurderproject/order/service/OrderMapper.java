package com.switchfully.eurderproject.order.service;

import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import com.switchfully.eurderproject.item_group.service.ItemGroupMapper;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderReportDTO;
import com.switchfully.eurderproject.order.api.dto.ReportDTO;
import com.switchfully.eurderproject.order.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ItemGroupMapper itemGroupMapper;

    public OrderMapper(ItemGroupMapper itemGroupMapper) {
        this.itemGroupMapper = itemGroupMapper;
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO()
                .setId(order.getId())
                .setCustomerId(order.getCustomer().getId())
                .setItemGroupDTOList(itemGroupMapper.toItemGroupDTO(order.getItemGroupList()))
                .setTotalPrice(order.getTotalPrice());
    }

    public Order toOrder(Customer customer, List<ItemGroup> itemGroupList) {
        return new Order(customer, itemGroupList);
    }

    public ReportDTO toReport(List<OrderReportDTO> customerOrders, double allOrderPrice) {
        return new ReportDTO(
                customerOrders,
                allOrderPrice
        );
    }

    public OrderReportDTO toOrderReportDTO(Order order) {
        return new OrderReportDTO(
                order.getId(),
                itemGroupMapper.toItemGroupReportDTO(order.getItemGroupList()),
                order.getTotalPrice()
        );
    }

    public List<OrderReportDTO> toOrderReportDTO(List<Order> customerOrders) {
        return customerOrders.stream()
                .map(this::toOrderReportDTO)
                .collect(Collectors.toList());
    }
}
