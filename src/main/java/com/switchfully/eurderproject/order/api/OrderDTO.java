package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.ItemGroup;

import java.util.List;

public class OrderDTO {
    private String id;
    private String customerId;
    private List<ItemGroupDTO> itemGroupDTOList;

    public OrderDTO setId(String id) {
        this.id = id;
        return this;
    }

    public OrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderDTO setItemGroupDTOList(List<ItemGroupDTO> itemGroupDTOList) {
        this.itemGroupDTOList = itemGroupDTOList;
        return this;
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
    public String toString() {
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemGroupDTOList=" + itemGroupDTOList +
                '}';
    }
}
