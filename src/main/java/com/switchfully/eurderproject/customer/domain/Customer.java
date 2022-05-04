package com.switchfully.eurderproject.customer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Transient
    private final Logger customerLogger = LoggerFactory.getLogger(Customer.class);

    @Id
    private String id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String address, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.firstName = validateFirstName(firstName);
        this.lastName = validateLastName(lastName);
        this.email = validateEmail(email);
        this.address = validateAddress(address);
        this.phoneNumber = validatePhoneNumber(phoneNumber);
        customerLogger.info("Successfully created new customer");
    }

    private String validateFirstName(String firstName) {
        validateAndCheckLoggingMessage(
                notFilledIn(firstName),
                "Customer can't be created without first name",
                "No first name given for new customer",
                "Successfully validated new customer's first name");
        return firstName;
    }

    private String validateLastName(String lastName) {
        validateAndCheckLoggingMessage(
                notFilledIn(lastName),
                "Customer can't be created without last name",
                "No last name given for new customer",
                "Successfully validated new customer's last name");
        return lastName;
    }

    private String validateEmail(String email) {
        validateAndCheckLoggingMessage(
                !isEmailFormValid(email),
                "Attempted to create customer with incorrect e-mail address format",
                "Incorrect format given for new customer's email address",
                "Successfully validated new customer's email address");
        return email;
    }

    private String validateAddress(String address) {
        validateAndCheckLoggingMessage(
                notFilledIn(address),
                "Customer can't be created without address",
                "No address given for new customer",
                "Successfully validated new customer's address");
        return address;
    }

    private String validatePhoneNumber(String phoneNumber) {
        validateAndCheckLoggingMessage(
                notFilledIn(phoneNumber),
                "Customer can't be created without phone number",
                "No phone number given for new customer",
                "Successfully validated new customer's phone number");
        return phoneNumber;
    }

    private void validateAndCheckLoggingMessage(boolean condition, String loggerErrorMessage, String jsonErrorMessage, String confirmationMessage) {
        if (condition) {
            customerLogger.error(loggerErrorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, jsonErrorMessage);
        }
        customerLogger.info(confirmationMessage);
    }

    private boolean notFilledIn(String string) {
        return string == null || string.isEmpty();
    }

    // retrieved from Rinaldo, which in turn got it somewhere from Stack Overflow (exact source unknown)
    private boolean isEmailFormValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
