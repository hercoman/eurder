package com.switchfully.eurderproject.item.domain;

import java.util.UUID;

public class Item {
    private final String id;
    private final String name;
    private final String description;
    private double price;
    private int amountAvailable;

    public Item(String name, String description, double price, int amountAvailable) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }
}
