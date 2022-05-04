package com.switchfully.eurderproject.order.api.dto;

import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;

import java.util.List;

public class OrderDTO {
    private String id;
    private String customerId;
    private List<ItemGroupDTO> itemGroupDTOList;
    private double totalPrice;

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

    public OrderDTO setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
