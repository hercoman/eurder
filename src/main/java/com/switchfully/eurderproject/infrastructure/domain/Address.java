package com.switchfully.eurderproject.infrastructure.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address {
    @Transient
    private final Logger addressLogger = LoggerFactory.getLogger(Address.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    @Column(name = "STREET_NAME")
    private String streetName;

    @Column(name = "STREET_NUMBER")
    private String streetNumber;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "FK_POSTAL_CODE_ID")
    private PostalCode postalCode;

    public Address() {
    }

    public Address(String streetName, String streetNumber, PostalCode postalCode) {
        this.streetName = validateStreetName(streetName);
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
    }

    private String validateStreetName(String streetName) {
        if (notFilledIn(streetName)) {
            addressLogger.error("Address can't be created without street name");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No street name given for new address");
        }
        addressLogger.info("Successfully validated new address's street name");
        return streetName;
    }

    private boolean notFilledIn(String string) {
        return string == null || string.isEmpty();
    }

    public Long getId() {
        return id;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }
}
