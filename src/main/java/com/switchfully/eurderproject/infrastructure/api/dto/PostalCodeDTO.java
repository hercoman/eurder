package com.switchfully.eurderproject.infrastructure.api.dto;

import java.util.Objects;

public class PostalCodeDTO {
    private final String zipcode;
    private final String city;

    public PostalCodeDTO(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalCodeDTO that = (PostalCodeDTO) o;
        return Objects.equals(zipcode, that.zipcode) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipcode, city);
    }
}
