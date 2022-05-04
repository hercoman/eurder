package com.switchfully.eurderproject.order.api.dto;

import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;

import java.util.List;

public class UpdatedCreateOrderDTO {
    private String customerId;
    private List<ItemGroupDTO> itemGroupDTOList;


    public UpdatedCreateOrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public UpdatedCreateOrderDTO setItemGroupDTOList(List<ItemGroupDTO> itemGroupDTOList) {
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
