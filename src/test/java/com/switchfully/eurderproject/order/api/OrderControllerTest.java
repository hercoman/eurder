package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import com.switchfully.eurderproject.item_group.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item.service.ItemMapper;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import com.switchfully.eurderproject.item_group.service.ItemGroupMapper;
import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
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
    private CustomerRepository customerRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemGroupMapper itemGroupMapper;

    @Test
    void createOrder_givenOrderToSave_thenOrderIsCreatedAndSavedCorrectly() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");
        // GIVEN
        Item item = new Item("Tomato", "A clean, round tomato with lots of vitamins", 0.125, 10);
        itemRepository.save(item);
        Customer customer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");
        customerRepository.save(customer);

        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO()
                .setItemId(item.getId())
                .setAmount(5);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO()
                .setItemId(item.getId())
                .setAmount(3);

        List<CreateItemGroupDTO> createItemGroupDTOList = Lists.newArrayList(createItemGroupDTO1, createItemGroupDTO2);

        // WHEN
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId(customer.getId())
                .setCreateItemGroupDTOList(createItemGroupDTOList);

        OrderDTO orderDTO =
                RestAssured
                        .given()
                        .port(port)
                        .body(createOrderDTO)
                        .contentType(JSON)
                        .when()
                        .accept(JSON)
                        .headers(httpHeaders)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDTO.class);

        // THEN
        Assertions.assertThat(orderDTO.getId()).isNotBlank();

        Assertions.assertThat(orderDTO.getCustomerId()).isEqualTo(customer.getId());

        Assertions.assertThat(orderDTO.getTotalPrice()).isEqualTo(1);
    }

    @Test
    void createOrder_givenOrderWithMultipleItems_thenOrderPriceCalculatedCorrectly() {
        // GIVEN
        Item item1 = new Item("Tomato", "A clean, round tomato with lots of vitamins", 0.125, 10);
        Item item2 = new Item("Carrot", "A carrot", 0.14, 30);
        List<Item> items = Lists.newArrayList(item1, item2);
        items.forEach(item -> itemRepository.save(item));
        Customer customer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");
        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO().setItemId(item1.getId()).setAmount(5);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO().setItemId(item2.getId()).setAmount(7);
        List<CreateItemGroupDTO> createItemGroupDTOList = Lists.newArrayList(createItemGroupDTO1, createItemGroupDTO2);
        List<ItemGroup> itemGroupList = itemGroupMapper.toItemGroup(createItemGroupDTOList);

        Order order = new Order(customer.getId(), itemGroupList);

        Assertions.assertThat(order.getTotalPrice()).isEqualTo(1.605);

    }

    @Test
    void createOrder_givenItemGroupToAddToOrderWithSufficientStock_thenAmountAvailableCorrectlyUpdatedInItemRepository() {
        // GIVEN
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");
        Item item = new Item("Tomato", "A clean, round tomato with lots of vitamins", 0.125, 10);
        itemRepository.save(item);
        Customer customer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");
        customerRepository.save(customer);
        CreateItemGroupDTO createItemGroupDTO = new CreateItemGroupDTO().setItemId(item.getId()).setAmount(5);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId(customer.getId())
                .setCreateItemGroupDTOList(Lists.newArrayList(createItemGroupDTO));

        OrderDTO orderDTO =
                RestAssured
                        .given()
                        .port(port)
                        .body(createOrderDTO)
                        .contentType(JSON)
                        .when()
                        .accept(JSON)
                        .headers(httpHeaders)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDTO.class);

        Assertions.assertThat(itemRepository.getItemById(item.getId()).getAmountAvailable()).isEqualTo(5);
    }

    @Test
    void createOrder_givenIncorrectCustomerId_thenGetHttpStatusBadRequest() {
        // GIVEN
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");
        Item item = new Item("Tomato", "A clean, round tomato with lots of vitamins", 0.125, 10);
        itemRepository.save(item);
        CreateItemGroupDTO createItemGroupDTO = new CreateItemGroupDTO().setItemId(item.getId()).setAmount(5);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId("notAnId")
                .setCreateItemGroupDTOList(Lists.newArrayList(createItemGroupDTO));

        RestAssured
                .given()
                .port(port)
                .body(createOrderDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .headers(httpHeaders)
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}