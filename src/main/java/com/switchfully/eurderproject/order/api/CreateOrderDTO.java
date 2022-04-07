package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.item.api.ItemGroupDTO;

import java.util.List;

public class CreateOrderDTO {
    private String customerId;
    private List<ItemGroupDTO> itemGroupDTOList;


    public CreateOrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public CreateOrderDTO setItemGroupDTOList(List<ItemGroupDTO> itemGroupDTOList) {
        this.itemGroupDTOList = itemGroupDTOList;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroupDTO> getItemGroupDTOList() {
        return itemGroupDTOList;
    }
}
