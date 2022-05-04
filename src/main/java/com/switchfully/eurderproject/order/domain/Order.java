package com.switchfully.eurderproject.order.domain;


import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final Logger orderLogger = LoggerFactory.getLogger(Order.class);

    private final String id;
    private final String customerId;
    private final List<ItemGroupDTO> itemGroupDTOList;
    private final double totalPrice;

    public Order(String customerId, List<ItemGroupDTO> itemGroupDTOList) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroupDTOList = itemGroupDTOList;
        this.totalPrice = calculateTotalPrice();
        orderLogger.info("Successfully created new order");
    }

    public double calculateTotalPrice() {
        return itemGroupDTOList.stream()
                .map(itemGroup -> itemGroup.getPricePerUnit() * itemGroup.getAmount())
                .reduce(0.0, Double::sum);
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroupDTO> getItemGroupDTOList() {
        return itemGroupDTOList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customerId, order.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemGroupDTOList=" + itemGroupDTOList +
                '}';
    }
}
