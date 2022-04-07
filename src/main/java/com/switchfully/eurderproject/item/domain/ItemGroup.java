package com.switchfully.eurderproject.item.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {
    private final Logger itemGroupLogger = LoggerFactory.getLogger(ItemGroup.class);

    private final String id;
    private final int amount;
    private final double pricePerUnit;
    private final LocalDate shippingDate;

    public ItemGroup(int amount, double pricePerUnit, LocalDate shippingDate) {
        this.id = UUID.randomUUID().toString();
        this.amount = validateAmount(amount);
        this.pricePerUnit = pricePerUnit;
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
}
