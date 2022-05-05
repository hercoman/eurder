package com.switchfully.eurderproject.item_group.api.dto;

import java.util.Objects;

public class ItemGroupReportDTO {
    private String name;
    private int orderedAmount;
    private double totalPrice;

    public ItemGroupReportDTO() {
    }

    public ItemGroupReportDTO(String name, int orderedAmount, double totalPrice) {
        this.name = name;
        this.orderedAmount = orderedAmount;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public int getOrderedAmount() {
        return orderedAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemGroupReportDTO that = (ItemGroupReportDTO) o;
        return orderedAmount == that.orderedAmount && Double.compare(that.totalPrice, totalPrice) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, orderedAmount, totalPrice);
    }

    @Override
    public String toString() {
        return "ItemGroupReportDTO{" +
                "name='" + name + '\'' +
                ", orderedAmount=" + orderedAmount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
