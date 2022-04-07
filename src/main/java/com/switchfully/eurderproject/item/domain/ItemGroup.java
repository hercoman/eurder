package com.switchfully.eurderproject.item.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ItemGroup {
    private final Logger itemGroupLogger = LoggerFactory.getLogger(ItemGroup.class);

    private final String id;
    private final String itemId;
    private final int amount;
    private final double pricePerUnit;
    private final LocalDate shippingDate;

    public ItemGroup(String itemId, int amount, double pricePerUnit, LocalDate shippingDate) {
        this.id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.amount = validateAmount(amount);
        this.pricePerUnit = validatePricePerUnit(pricePerUnit);
        this.shippingDate = shippingDate;
    }

    private int validateAmount(int amount) {
        validateAndCheckLoggingMessage(
                (amount < 0),
                "Negative amount input for item group",
                "Unable to create item group with negative amount",
                "Successfully validated new item group amount");
        return amount;
    }

    private double validatePricePerUnit(double pricePerUnit) {
        validateAndCheckLoggingMessage(
                (pricePerUnit < 0),
                "Negative price per unit input for item group",
                "Unable to create item group with negative price per unit",
                "Successfully validated new item group price per unit");
        return pricePerUnit;
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

    private void validateAndCheckLoggingMessage(boolean condition, String loggerErrorMessage, String jsonErrorMessage, String confirmationMessage) {
        if (condition) {
            itemGroupLogger.error(loggerErrorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, jsonErrorMessage);
        }
        itemGroupLogger.info(confirmationMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemGroup itemGroup = (ItemGroup) o;
        return amount == itemGroup.amount && Objects.equals(id, itemGroup.id) && Objects.equals(itemId, itemGroup.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, amount);
    }

    @Override
    public String toString() {
        return "ItemGroup{" +
                "id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                ", amount=" + amount +
                ", pricePerUnit=" + pricePerUnit +
                ", shippingDate=" + shippingDate +
                '}';
    }
}
