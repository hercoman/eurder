package com.switchfully.eurderproject.item.domain;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {
    private final String id;
    private final int amount;
    private final double pricePerUnit;
    private final LocalDate shippingDate;

    public ItemGroup(int amount, double pricePerUnit, LocalDate shippingDate) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
        this.shippingDate = shippingDate;
    }

    public String getId() {
        return id;
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
}
