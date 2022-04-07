package com.switchfully.eurderproject.item.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

public class Item {
    private final Logger itemLogger = LoggerFactory.getLogger(Item.class);

    private final String id;
    private final String name;
    private final String description;
    private final double price;
    private final int amountAvailable;

    public Item(String name, String description, double price, int amountAvailable) {
        this.id = UUID.randomUUID().toString();
        this.name = validateName(name);
        this.description = validateDescription(description);
        this.price = validatePrice(price);
        this.amountAvailable = validateAmountAvailable(amountAvailable);
    }

    private String validateName(String name) {
        validateAndCheckLoggingMessage(
                notFilledIn(name),
                "Item can't be created without name",
                "No name given for new item",
                "Successfully validated new item name");
        return name;
    }

    private String validateDescription(String description) {
        validateAndCheckLoggingMessage(
                notFilledIn(description),
                "Item can't be created without description",
                "No description given for new item",
                "Successfully validated new item description");
        return description;
    }

    private double validatePrice(double price) {
        validateAndCheckLoggingMessage(
                (price < 0),
                "Item can't be created with a negative price",
                "Negative price for new item is not allowed",
                "Successfully validated new item price");
        return price;
    }

    private int validateAmountAvailable(int amountAvailable) {
        validateAndCheckLoggingMessage(
                (amountAvailable < 0),
                "Item can't be created with a negative available amount",
                "Negative available amount for new item is not allowed",
                "Successfully validated new item available amount");
        return amountAvailable;
    }

    private void validateAndCheckLoggingMessage(boolean condition, String loggerErrorMessage, String jsonErrorMessage, String confirmationMessage) {
        if (condition) {
            itemLogger.error(loggerErrorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, jsonErrorMessage);
        }
        itemLogger.info(confirmationMessage);
    }

    private boolean notFilledIn(String string) {
        return string == null || string.isEmpty();
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
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
