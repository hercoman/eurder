package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.item.domain.ItemGroup;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OrderDTO {
    private String id;
    private String customerId;
    private List<ItemGroup> itemGroupList;

    public OrderDTO setId(String id) {
        this.id = id;
        return this;
    }

    public OrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderDTO setItemGroupList(List<ItemGroup> itemGroupList) {
        this.itemGroupList = itemGroupList;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) && Objects.equals(customerId, orderDTO.customerId) && Objects.equals(itemGroupList, orderDTO.itemGroupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, itemGroupList);
    }
}
