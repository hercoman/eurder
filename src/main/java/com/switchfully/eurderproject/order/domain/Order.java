package com.switchfully.eurderproject.order.domain;


import com.switchfully.eurderproject.item.api.ItemGroupDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final List<ItemGroupDTO> itemGroupDTOList;

    public Order(String customerId, List<ItemGroupDTO> itemGroupDTOList) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroupDTOList = itemGroupDTOList;
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
