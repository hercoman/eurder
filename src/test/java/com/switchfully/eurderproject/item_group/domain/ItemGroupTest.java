package com.switchfully.eurderproject.item_group.domain;

import com.switchfully.eurderproject.item_group.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.service.OrderService;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class ItemGroupTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OrderService orderService;

    @Test
    void createItemGroup_givenAnItemGroupWithNegativeAmount_thenGetHttpStatusBadRequest() {
        List<CreateItemGroupDTO> createItemGroupDTOList = Lists.newArrayList(new CreateItemGroupDTO()
                .setItemId("123e4567-e89b-12d3-a456-426614174002")
                .setAmount(-5));

        Assertions.assertThatThrownBy(() -> orderService.createOrder(
                        new CreateOrderDTO()
                                .setCustomerId("123e4567-e89b-12d3-a456-426614174000")
                                .setCreateItemGroupDTOList(createItemGroupDTOList)))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Unable to create item group with negative amount");
    }

    @Test
    void createItemGroup_givenAnItemGroupWithNonExistingId_thenGetHttpStatusBadRequest() {
        List<CreateItemGroupDTO> createItemGroupDTOList = Lists.newArrayList(new CreateItemGroupDTO()
                .setItemId("derp")
                .setAmount(5));

        Assertions.assertThatThrownBy(() -> orderService.createOrder(
                        new CreateOrderDTO()
                                .setCustomerId("123e4567-e89b-12d3-a456-426614174000")
                                .setCreateItemGroupDTOList(createItemGroupDTOList)))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("No item found for id derp");
    }
}