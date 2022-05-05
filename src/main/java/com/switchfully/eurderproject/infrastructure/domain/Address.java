package com.switchfully.eurderproject.infrastructure.domain;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address {

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
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
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
