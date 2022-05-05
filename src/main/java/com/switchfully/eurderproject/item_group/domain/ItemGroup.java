package com.switchfully.eurderproject.item_group.domain;

import com.switchfully.eurderproject.item.domain.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ITEM_GROUP")
public class ItemGroup {
    @Transient
    private final Logger itemGroupLogger = LoggerFactory.getLogger(ItemGroup.class);

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "FK_ITEM_ID")
    private Item item;
    @Column(name = "AMOUNT")
    private int amount;
    @Column(name = "PRICE")
    private double pricePerUnit;
    @Column(name = "SHIPPING_DATE")
    private LocalDate shippingDate;

    public ItemGroup() {
    }

    public ItemGroup(Item item, int amount) {
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.amount = validateAmount(amount);
        this.pricePerUnit = item.getPrice();
        this.shippingDate = calculateShippingDate(amount, item.getAmountAvailable());
    }

    private int validateAmount(int amount) {
        if (amount < 0) {
            itemGroupLogger.error("Negative amount input for item group");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create item group with negative amount");
        }
        itemGroupLogger.info("Successfully validated new item group amount");
        return amount;
    }

    private LocalDate calculateShippingDate(int amount, int amountAvailable) {
        if (amount <= amountAvailable) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.now().plusDays(7);
    }

    public String getId() {
        return id;
    }

    public Item getItem() {
        return item;
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
        return amount == itemGroup.amount && Objects.equals(id, itemGroup.id) && Objects.equals(item, itemGroup.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item, amount);
    }

    @Override
    public String toString() {
        return "ItemGroup{" +
                "id='" + id + '\'' +
                ", item='" + item + '\'' +
                ", amount=" + amount +
                ", pricePerUnit=" + pricePerUnit +
                ", shippingDate=" + shippingDate +
                '}';
    }
}
