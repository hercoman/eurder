package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.ItemGroup;

import java.util.List;

public class SavedOrderDTO {
    private String customerId;
    private List<ItemGroupDTO> itemGroupDTOList;


    public SavedOrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public SavedOrderDTO setItemGroupDTOList(List<ItemGroupDTO> itemGroupDTOList) {
        this.itemGroupDTOList = itemGroupDTOList;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroupDTO> getItemGroupDTOList() {
        return itemGroupDTOList;
    }

    @Override
    public String toString() {
        return "SavedOrderDTO{" +
                "customerId='" + customerId + '\'' +
                ", itemGroupDTOList=" + itemGroupDTOList +
                '}';
    }
}
