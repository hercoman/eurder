package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.customer.api.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemGroup;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item.service.ItemMapper;
import com.switchfully.eurderproject.order.domain.Order;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    void createOrder_givenOrderToSave_thenOrderIsCreatedAndSavedCorrectly() {
        Item item = new Item("Tomato", "A clean, round tomato with lots of vitamins", 0.125, 10);
        itemRepository.save(item);
        Customer customer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");

        ItemGroupDTO itemGroupDTO1 = new ItemGroupDTO()
                .setItemId(item.getId())
                .setAmount(5);
        ItemGroup itemGroup1 = itemMapper.toItemGroup(itemGroupDTO1);

        List<ItemGroupDTO> itemGroupDTOList = Lists.newArrayList(itemGroupDTO1);

        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId(customer.getId())
                .setItemGroupDTOList(itemGroupDTOList);

        OrderDTO orderDTO =
                RestAssured
                        .given()
                        .port(port)
                        .body(createOrderDTO)
                        .contentType(JSON)
                        .when()
                        .accept(JSON)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDTO.class);

        String expectedCustomerId = customer.getId();
        List<ItemGroup> expectedItemGroupList = Lists.newArrayList(itemGroup1);
        Assertions.assertThat(orderDTO.getId()).isNotBlank();
        Assertions.assertThat(orderDTO.getCustomerId()).isEqualTo(expectedCustomerId);
        Assertions.assertThat(orderDTO.getItemGroupList()).isEqualTo(expectedItemGroupList);
    }
}