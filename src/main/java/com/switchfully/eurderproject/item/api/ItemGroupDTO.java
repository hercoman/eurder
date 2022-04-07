package com.switchfully.eurderproject.item.api;

public class ItemGroupDTO {
    private String itemId;
    private int amount;

    public ItemGroupDTO setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public ItemGroupDTO setAmount(int amount) {
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
