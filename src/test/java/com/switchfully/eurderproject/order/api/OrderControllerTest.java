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
    void addItemToOrder_givenItemToSaveInOrder_thenItemGroupIscreatedCorrectly() {
        Item item = new Item("Tomato", "A clean, round tomato with lots of vitamins", 0.125, 10);
        itemRepository.save(item);
        Customer customer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");
        ItemGroupDTO expectedItemGroupDTO = new ItemGroupDTO()
                .setItemId(item.getId())
                .setAmount(5);
        ItemGroup expectedItemGroup = itemMapper.toItemGroup(expectedItemGroupDTO);
        List<ItemGroup> itemGroupList = Lists.newArrayList(expectedItemGroup);

        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId(customer.getId())
                .setItemGroupList(itemGroupList);

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

        Assertions.assertThat(orderDTO.getId()).isNotBlank();
        Assertions.assertThat(orderDTO.getCustomerId()).isEqualTo(customer.getId());
        Assertions.assertThat(orderDTO.getItemGroupList()).isEqualTo(itemGroupList);
    }
}