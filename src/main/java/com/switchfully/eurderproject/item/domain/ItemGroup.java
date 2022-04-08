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
        this.pricePerUnit = pricePerUnit;
        this.shippingDate = shippingDate;
        itemGroupLogger.info("Successfully created new item group");
    }

    private int validateAmount(int amount) {
        if (amount < 0) {
            itemGroupLogger.error("Negative amount input for item group");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create item group with negative amount");
        }
        itemGroupLogger.info("Successfully validated new item group amount");
        return amount;
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
