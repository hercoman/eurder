package com.switchfully.eurderproject.infrastructure.api.dto;

import java.util.Objects;

public class AddressDTO {
    private final String streetName;
    private final String streetNumber;
    private final PostalCodeDTO postalCode;

    public AddressDTO(String streetName, String streetNumber, PostalCodeDTO postalCode) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public PostalCodeDTO getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(streetName, that.streetName) && Objects.equals(streetNumber, that.streetNumber) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, postalCode);
    }
}
