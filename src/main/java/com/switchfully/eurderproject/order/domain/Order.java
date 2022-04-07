package com.switchfully.eurderproject.order.domain;

import com.switchfully.eurderproject.item.domain.ItemGroup;

import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final List<ItemGroup> itemGroupList;

    public Order(String customerId, List<ItemGroup> itemGroupList) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroupList = itemGroupList;
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
}
