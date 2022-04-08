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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

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
        Customer repoCustomer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");
        customerRepository.save(repoCustomer);
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstName("John")
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
        List<Customer> customerList = Lists.newArrayList(
                new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999"),
                new Customer("Jake", "Peralte", "jake.peralta@diehard.com", "Brooklyn Street, 26000 USA", "0800-999"));
        customerList.forEach(customer -> customerRepository.save(customer));

        CustomerDTO[] result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerDTO[].class);

        List<CustomerDTO> expectedList = customerMapper.toDTO(customerList);
        Assertions.assertThat(List.of(result)).hasSameElementsAs(expectedList);
    }

    @Test
    void getSingleCustomer_customerIsShownCorrectly() {
        Customer testCustomer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");
        List<Customer> customerList = Lists.newArrayList(
                testCustomer,
                new Customer("Jake", "Peralte", "jake.peralta@diehard.com", "Brooklyn Street, 26000 USA", "0800-999"));
        customerList.forEach(customer -> customerRepository.save(customer));

        CustomerDTO result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/customers/" + testCustomer.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerDTO.class);

        Assertions.assertThat(result).isEqualTo(customerMapper.toDTO(testCustomer));
    }

    @Test
    void getSingleCustomerWithNonExistingId_ThenGetHttpStatusBadRequest() {
        Customer testCustomer = new Customer("John", "McClane", "john.mcclane@diehard.com", "Hero Street, 26000 USA", "0800-999");
        List<Customer> customerList = Lists.newArrayList(
                testCustomer,
                new Customer("Jake", "Peralte", "jake.peralta@diehard.com", "Brooklyn Street, 26000 USA", "0800-999"));
        customerList.forEach(customer -> customerRepository.save(customer));

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