package com.switchfully.eurderproject.item.api;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void createItem_givenItemToCreate_thenTheNewlyCreatedItemIsSavedAndReturned() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName("Tomato")
                .setDescription("A clean, round tomato with lots of vitamins")
                .setPrice(0.125)
                .setAmountAvailable(10);

        ItemDTO itemDTO =
                RestAssured
                        .given()
                        .port(port)
                        .body(createItemDTO)
                        .contentType(JSON)
                        .when()
                        .accept(JSON)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(ItemDTO.class);

        Assertions.assertThat(itemDTO.getId()).isNotBlank();
        Assertions.assertThat(itemDTO.getName()).isEqualTo(createItemDTO.getName());
        Assertions.assertThat(itemDTO.getDescription()).isEqualTo(createItemDTO.getDescription());
        Assertions.assertThat(itemDTO.getPrice()).isEqualTo(createItemDTO.getPrice());
        Assertions.assertThat(itemDTO.getAmountAvailable()).isEqualTo(createItemDTO.getAmountAvailable());
    }

    @Test
    void createItem_givenItemWithoutName_thenGetHttpStatusBadRequest() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setDescription("A clean, round tomato with lots of vitamins")
                .setPrice(0.125)
                .setAmountAvailable(10);

        RestAssured
                .given()
                .port(port)
                .body(createItemDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createItem_givenItemWithoutDescription_thenGetHttpStatusBadRequest() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName("Tomato")
                .setPrice(0.125)
                .setAmountAvailable(10);

        RestAssured
                .given()
                .port(port)
                .body(createItemDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createItem_givenItemWithNegativePrice_thenGetHttpStatusBadRequest() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName("Tomato")
                .setDescription("A clean, round tomato with lots of vitamins")
                .setPrice(-0.125)
                .setAmountAvailable(10);

        RestAssured
                .given()
                .port(port)
                .body(createItemDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createItem_givenItemWithoutPrice_thenGetHttpStatusBadRequest() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName("Tomato")
                .setDescription("A clean, round tomato with lots of vitamins")
                .setAmountAvailable(10);

        RestAssured
                .given()
                .port(port)
                .body(createItemDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createItem_givenItemWithNegativeAmountAvailable_thenGetHttpStatusBadRequest() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName("Tomato")
                .setDescription("A clean, round tomato with lots of vitamins")
                .setPrice(0.125)
                .setAmountAvailable(-10);

        RestAssured
                .given()
                .port(port)
                .body(createItemDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createItem_givenItemWithoutAmountAvailable_thenGetHttpStatusBadRequest() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName("Tomato")
                .setDescription("A clean, round tomato with lots of vitamins")
                .setPrice(0.125);

        RestAssured
                .given()
                .port(port)
                .body(createItemDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}