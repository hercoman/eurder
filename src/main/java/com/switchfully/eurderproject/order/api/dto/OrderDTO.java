package com.switchfully.eurderproject.order.api.dto;

public class OrderDTO {
    private String id;
    private String customerId;
    private double totalPrice;

    public OrderDTO setId(String id) {
        this.id = id;
        return this;
    }

    public OrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderDTO setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
