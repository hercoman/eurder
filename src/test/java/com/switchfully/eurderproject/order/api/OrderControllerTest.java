package com.switchfully.eurderproject.order.api;

import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item_group.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import com.switchfully.eurderproject.order.api.dto.CreateOrderDTO;
import com.switchfully.eurderproject.order.api.dto.OrderDTO;
import com.switchfully.eurderproject.order.domain.Order;
import com.switchfully.eurderproject.order.service.OrderService;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class OrderControllerTest {

    public static final String TEST_ITEM_ID1 = "123e4567-e89b-12d3-a456-426614174002";
    public static final String TEST_ITEM_ID2 = "123e4567-e89b-12d3-a456-426614174003";
    public static final String TEST_CUSTOMER_ID = "123e4567-e89b-12d3-a456-426614174000";
    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;

    @Test
    void createOrder_givenOrderToSave_thenOrderIsCreatedAndSavedCorrectly() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");
        // GIVEN

        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO()
                .setItemId(TEST_ITEM_ID1)
                .setAmount(5);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO()
                .setItemId(TEST_ITEM_ID1)
                .setAmount(3);

        List<CreateItemGroupDTO> createItemGroupDTOList = Lists.newArrayList(createItemGroupDTO1, createItemGroupDTO2);

        // WHEN
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId(TEST_CUSTOMER_ID)
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

        Assertions.assertThat(orderDTO.getCustomerId()).isEqualTo(customerRepository.getById(TEST_CUSTOMER_ID).getId());

        Assertions.assertThat(orderDTO.getTotalPrice()).isEqualTo(1);

        List<ItemGroupDTO> expectedItemGroupDTOList = Lists.newArrayList(
                new ItemGroupDTO()
                        .setId(orderDTO.getItemGroupDTOList().get(0).getId())
                        .setItemId(TEST_ITEM_ID1)
                        .setAmount(5)
                        .setPricePerUnit(0.125)
                        .setShippingDate(LocalDate.now().plusDays(1)),
                new ItemGroupDTO()
                        .setId(orderDTO.getItemGroupDTOList().get(1).getId())
                        .setItemId(TEST_ITEM_ID1)
                        .setAmount(3)
                        .setPricePerUnit(0.125)
                        .setShippingDate(LocalDate.now().plusDays(1)));
        Assertions.assertThat(orderDTO.getItemGroupDTOList()).isEqualTo(expectedItemGroupDTOList);
    }

    @Test
    void createOrder_givenOrderWithMultipleItems_thenOrderPriceCalculatedCorrectly() {
        // GIVEN
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");
        // GIVEN

        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO()
                .setItemId(TEST_ITEM_ID1)
                .setAmount(5);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO()
                .setItemId(TEST_ITEM_ID2)
                .setAmount(7);

        List<CreateItemGroupDTO> createItemGroupDTOList = Lists.newArrayList(createItemGroupDTO1, createItemGroupDTO2);

        // WHEN
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId(TEST_CUSTOMER_ID)
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
        Assertions.assertThat(orderDTO.getTotalPrice()).isEqualTo(1.605);



    }

    @Test
    void createOrder_givenItemGroupToAddToOrderWithSufficientStock_thenAmountAvailableCorrectlyUpdatedInItemRepository() {
        // GIVEN
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");
        CreateItemGroupDTO createItemGroupDTO = new CreateItemGroupDTO().setItemId(TEST_ITEM_ID1).setAmount(5);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setCustomerId(TEST_CUSTOMER_ID)
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
                        .statusCode(HttpStatus.CREATED.value());

        Assertions.assertThat(orderService.findAmountForItem(TEST_ITEM_ID1)).isEqualTo(5);
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