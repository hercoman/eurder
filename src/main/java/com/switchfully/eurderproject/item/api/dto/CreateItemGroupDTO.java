package com.switchfully.eurderproject.item.api.dto;

public class CreateItemGroupDTO {
    private String itemId;
    private int amount;

    public CreateItemGroupDTO setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public CreateItemGroupDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ItemGroupDTO{" +
                "itemId='" + itemId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
