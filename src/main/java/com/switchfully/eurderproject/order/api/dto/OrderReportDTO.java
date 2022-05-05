package com.switchfully.eurderproject.order.api.dto;

import com.google.common.collect.Lists;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupReportDTO;

import java.util.List;

public class OrderReportDTO {
    private String orderId;
    private List<ItemGroupReportDTO> orderedItemGroups;
    private double totalOrderPrice;

    public OrderReportDTO() {
    }

    public OrderReportDTO(String orderId, List<ItemGroupReportDTO> orderedItemGroups, double totalOrderPrice) {
        this.orderId = orderId;
        this.orderedItemGroups = orderedItemGroups;
        this.totalOrderPrice = totalOrderPrice;
    }

    public List<ItemGroupReportDTO> getOrderedItemGroups() {
        return orderedItemGroups;
    }

    public double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    @Override
    public String toString() {
        return "OrderReportDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderedItemGroups=" + orderedItemGroups +
                ", totalOrderPrice=" + totalOrderPrice +
                '}';
    }
}
