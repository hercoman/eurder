package com.switchfully.eurderproject.item_group.api.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ItemGroupDTO {
    private String id;
    private String itemId;
    private int amount;
    private double pricePerUnit;
    private LocalDate shippingDate;

    public ItemGroupDTO setId(String id) {
        this.id = id;
        return this;
    }

    public ItemGroupDTO setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public ItemGroupDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemGroupDTO setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public ItemGroupDTO setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemGroupDTO that = (ItemGroupDTO) o;
        return amount == that.amount && Double.compare(that.pricePerUnit, pricePerUnit) == 0 && Objects.equals(itemId, that.itemId) && Objects.equals(shippingDate, that.shippingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, amount, pricePerUnit, shippingDate);
    }

    @Override
    public String toString() {
        return "ItemGroupDTO{" +
                "id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                ", amount=" + amount +
                ", pricePerUnit=" + pricePerUnit +
                ", shippingDate=" + shippingDate +
                '}';
    }
}
