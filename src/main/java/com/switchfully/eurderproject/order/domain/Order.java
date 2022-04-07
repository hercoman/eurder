package com.switchfully.eurderproject.order.domain;

import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void addItemToOrder(String itemId, int amount) {
        ItemGroupDTO itemGroupToAdd = new ItemGroupDTO()
                .setItemId(itemId)
                .setAmount(amount);

    }

    public ItemGroup getItemGroup(int index) {
        return itemGroupList.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customerId, order.customerId) && Objects.equals(itemGroupList, order.itemGroupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, itemGroupList);
    }
}
