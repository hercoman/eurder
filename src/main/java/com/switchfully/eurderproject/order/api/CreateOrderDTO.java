package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.item.domain.ItemGroup;

import java.util.List;

public class CreateOrderDTO {
    private String customerId;
    private List<ItemGroup> itemGroupList;

    public CreateOrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public CreateOrderDTO setItemGroupList(List<ItemGroup> itemGroupList) {
        this.itemGroupList = itemGroupList;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }
}
