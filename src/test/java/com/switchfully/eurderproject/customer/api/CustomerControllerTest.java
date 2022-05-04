package com.switchfully.eurderproject.customer.api;

import com.switchfully.eurderproject.customer.api.dto.CreateCustomerDTO;
import com.switchfully.eurderproject.customer.api.dto.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.customer.domain.CustomerRepository;
import com.switchfully.eurderproject.customer.service.CustomerMapper;
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

import java.util.List;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void createCustomer_givenACustomerToCreate_thenTheNewlyCreatedCustomerIsSavedAndReturned() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstName("John")
                .setLastName("McClane")
                .setEmail("john.mcclane@diehard.com")
                .setAddress("Hero Street, 26000 USA")
                .setPhoneNumber("0800-999");

        CustomerDTO customerDTO =
                RestAssured
                        .given()
                        .port(port)
                        .body(createCustomerDTO)
                        .contentType(JSON)
                        .when()
                        .accept(JSON)
                        .post("/customers")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(CustomerDTO.class);

        Assertions.assertThat(customerDTO.getId()).isNotBlank();
        Assertions.assertThat(customerDTO.getFirstName()).isEqualTo(createCustomerDTO.getFirstName());
        Assertions.assertThat(customerDTO.getLastName()).isEqualTo(createCustomerDTO.getLastName());
        Assertions.assertThat(customerDTO.getEmail()).isEqualTo(createCustomerDTO.getEmail());
        Assertions.assertThat(customerDTO.getAddress()).isEqualTo(createCustomerDTO.getAddress());
        Assertions.assertThat(customerDTO.getPhoneNumber()).isEqualTo(createCustomerDTO.getPhoneNumber());
    }

    @Test
    void createCustomer_givenACustomerWithoutFirstName_thenGetHttpStatusBadRequest() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setLastName("McClane")
                .setEmail("john.mcclane@diehard.com")
                .setAddress("Hero Street, 26000 USA")
                .setPhoneNumber("0800-999");

        RestAssured
                        .given()
                        .port(port)
                        .body(createCustomerDTO)
                        .contentType(JSON)
                        .when()
                        .accept(JSON)
                        .post("/customers")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createCustomer_givenACustomerWithoutLastName_thenGetHttpStatusBadRequest() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstName("John")
                .setEmail("john.mcclane@diehard.com")
                .setAddress("Hero Street, 26000 USA")
                .setPhoneNumber("0800-999");

        RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createCustomer_givenACustomerWithIncorrectEmail_thenGetHttpStatusBadRequest() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstName("John")
                .setLastName("McClane")
                .setEmail("john.mcclane.diehard.com")
                .setAddress("Hero Street, 26000 USA")
                .setPhoneNumber("0800-999");

        RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createCustomer_givenACustomerWithExistingEmail_thenGetHttpStatusBadRequest() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstName("John")
                .setLastName("McClane")
                .setEmail("John.McTest@diehard.com")
                .setAddress("Hero Street, 26000 USA")
                .setPhoneNumber("0800-999");

        RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createCustomer_givenACustomerWithoutAddress_thenGetHttpStatusBadRequest() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstName("John")
                .setLastName("McClane")
                .setEmail("john.mcclane@diehard.com")
                .setPhoneNumber("0800-999");

        RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createCustomer_givenACustomerWithoutPhoneNumber_thenGetHttpStatusBadRequest() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstName("John")
                .setLastName("McClane")
                .setEmail("john.mcclane@diehard.com")
                .setAddress("Hero Street, 26000 USA");

        RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getAllCustomers_customersAreShownCorrectly() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");

        CustomerDTO[] result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .headers(httpHeaders)
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerDTO[].class);

        List<CustomerDTO> expectedList = customerMapper.toDTO(customerRepository.getAll());
        Assertions.assertThat(List.of(result)).hasSameElementsAs(expectedList);
    }

    @Test
    void getSingleCustomer_customerIsShownCorrectly() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic SGVyYmVydDpTd2l0Y2gx");
        Customer actualCustomer = customerRepository.getCustomerById("123e4567-e89b-12d3-a456-426614174000");
        Customer expectedCustomer = new Customer("John", "McClane", "John.McTest@diehard.com", "Hero Street, 26000 USA", "0800-999");

        CustomerDTO result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .headers(httpHeaders)
                .get("/customers/" + actualCustomer.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerDTO.class);

        Assertions.assertThat(result).isEqualTo(customerMapper.toDTO(expectedCustomer));
    }

    @Test
    void getSingleCustomerWithNonExistingId_ThenGetHttpStatusBadRequest() {
        RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/customers/derp")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}