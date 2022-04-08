package com.switchfully.eurderproject.item.api.dto;

public class CreateItemDTO {
    private String name;
    private String description;
    private double price;
    private int amountAvailable;

    public CreateItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public CreateItemDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateItemDTO setPrice(double price) {
        this.price = price;
        return this;
    }

    public CreateItemDTO setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
        return this;
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
