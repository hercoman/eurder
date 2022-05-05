package com.switchfully.eurderproject.infrastructure.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;

@Entity
@Table(name = "POSTAL_CODE")
public class PostalCode {
    @Transient
    private final Logger postalCodeLogger = LoggerFactory.getLogger(PostalCode.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postal_code_seq")
    @SequenceGenerator(name = "postal_code_seq", sequenceName = "postal_code_seq", allocationSize = 1)
    private Long id;

    @Column(name = "ZIP_CODE")
    private String zipcode;

    @Column(name = "CITY")
    private String city;

    public PostalCode() {
    }

    public PostalCode(String zipcode, String city) {
        this.zipcode = validateZipcode(zipcode);
        this.city = validateCity(city);
    }

    private String validateZipcode(String zipcode) {
        validateAndCheckLoggingMessage(
                notFilledIn(zipcode),
                "Postal code can't be created without zipcode",
                "No zipcode given for new postal code",
                "Successfully validated new postal code's zipcode");
        return zipcode;
    }

    private String validateCity(String city) {
        validateAndCheckLoggingMessage(
                notFilledIn(city),
                "Postal code can't be created without city",
                "No city given for new postal code",
                "Successfully validated new postal code's city");
        return city;
    }

    private void validateAndCheckLoggingMessage(boolean condition, String loggerErrorMessage, String jsonErrorMessage, String confirmationMessage) {
        if (condition) {
            postalCodeLogger.error(loggerErrorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, jsonErrorMessage);
        }
        postalCodeLogger.info(confirmationMessage);
    }

    private boolean notFilledIn(String string) {
        return string == null || string.isEmpty();
    }

    public Long getId() {
        return id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }
}
