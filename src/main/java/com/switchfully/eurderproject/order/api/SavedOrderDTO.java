package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.ItemGroup;

import java.util.List;

public class SavedOrderDTO {
    private String customerId;
    private List<ItemGroup> itemGroupList;


    public SavedOrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public SavedOrderDTO setItemGroupList(List<ItemGroup> itemGroupList) {
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
