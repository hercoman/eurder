package com.switchfully.eurderproject.order.domain;


import com.switchfully.eurderproject.item.api.dto.ItemGroupDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final List<ItemGroupDTO> itemGroupDTOList;
    private final double totalPrice;

    public Order(String customerId, List<ItemGroupDTO> itemGroupDTOList) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroupDTOList = itemGroupDTOList;
        this.totalPrice = calculateTotalPrice();
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

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (ItemGroupDTO itemGroup : itemGroupDTOList) {
            totalPrice += (itemGroup.getAmount() * itemGroup.getPricePerUnit());
        }
        return totalPrice;
    }
}
