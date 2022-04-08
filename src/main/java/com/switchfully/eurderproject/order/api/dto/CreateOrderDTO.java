package com.switchfully.eurderproject.order.api.dto;

import com.switchfully.eurderproject.item.api.dto.CreateItemGroupDTO;

import java.util.List;

public class CreateOrderDTO {
    private String customerId;
    private List<CreateItemGroupDTO> createItemGroupDTOList;


    public CreateOrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public CreateOrderDTO setCreateItemGroupDTOList(List<CreateItemGroupDTO> createItemGroupDTOList) {
        this.createItemGroupDTOList = createItemGroupDTOList;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<CreateItemGroupDTO> getCreateItemGroupDTOList() {
        return createItemGroupDTOList;
    }

    @Override
    public String toString() {
        return "CreateOrderDTO{" +
                "customerId='" + customerId + '\'' +
                ", createItemGroupDTOList=" + createItemGroupDTOList +
                '}';
    }
}
