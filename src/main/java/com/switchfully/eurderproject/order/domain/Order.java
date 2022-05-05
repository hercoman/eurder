package com.switchfully.eurderproject.order.domain;


import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "EURDER")
public class Order {
    @Transient
    private final Logger orderLogger = LoggerFactory.getLogger(Order.class);

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "FK_CUSTOMER_ID")
    private Customer customer;
    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "FK_EURDER_ID", nullable = false)
    private List<ItemGroup> itemGroupList;
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    public Order() {
    }

    public Order(Customer customer, List<ItemGroup> itemGroupList) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.itemGroupList = itemGroupList;
        this.totalPrice = calculateTotalPrice();
        orderLogger.info("Successfully created new order");
    }

    public double calculateTotalPrice() {
        return itemGroupList.stream()
                .map(itemGroup -> itemGroup.getPricePerUnit() * itemGroup.getAmount())
                .reduce(0.0, Double::sum);
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customer='" + customer + '\'' +
                ", itemGroupDTOList=" + itemGroupList +
                '}';
    }
}
