package com.switchfully.eurderproject.infrastructure.domain;

import javax.persistence.*;

@Entity
@Table(name = "POSTAL_CODE")
public class PostalCode {

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
        this.zipcode = zipcode;
        this.city = city;
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
