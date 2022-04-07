package com.switchfully.eurderproject.item.api;

public class ItemDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private int amountAvailable;

    public ItemDTO setId(String id) {
        this.id = id;
        return this;
    }
    public ItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public ItemDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemDTO setPrice(double price) {
        this.price = price;
        return this;
    }

    public ItemDTO setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
        return this;
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
