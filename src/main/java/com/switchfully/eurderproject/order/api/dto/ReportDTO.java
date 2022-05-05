package com.switchfully.eurderproject.order.api.dto;

import java.util.List;

public class ReportDTO {
    private List<OrderReportDTO> orderList;
    private double allOrderPrice;

    public ReportDTO() {
    }

    public ReportDTO(List<OrderReportDTO> orderList, double allOrderPrice) {
        this.orderList = orderList;
        this.allOrderPrice = allOrderPrice;
    }

    public List<OrderReportDTO> getOrderList() {
        return orderList;
    }

    public double getAllOrderPrice() {
        return allOrderPrice;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "orderList=" + orderList +
                ", allOrderPrice=" + allOrderPrice +
                '}';
    }
}
