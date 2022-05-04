package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.service.OrderService;
import com.switchfully.eurderproject.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.switchfully.eurderproject.security.Feature.ORDER_ITEMS;

@RestController
@RequestMapping(path = "orders")
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestHeader String authorization, @RequestBody CreateOrderDTO createOrderDTO) {
        securityService.validateAuthorization(authorization, ORDER_ITEMS);
        return orderService.createOrder(createOrderDTO);
    }

}
