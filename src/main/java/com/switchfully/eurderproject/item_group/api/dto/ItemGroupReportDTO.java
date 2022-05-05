package com.switchfully.eurderproject.item_group.api.dto;

public class ItemGroupReportDTO {
    private String name;
    private int orderedAmount;
    private int totalPrice;

    public ItemGroupReportDTO() {
    }

    public ItemGroupReportDTO(String name, int orderedAmount, int totalPrice) {
        this.name = name;
        this.orderedAmount = orderedAmount;
        this.totalPrice = totalPrice;
    }
}
