package com.switchfully.eurderproject.item.api.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return Objects.equals(id, itemDTO.id) && Objects.equals(name, itemDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
