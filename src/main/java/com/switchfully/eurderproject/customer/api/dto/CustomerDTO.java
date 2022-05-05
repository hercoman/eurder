package com.switchfully.eurderproject.customer.api.dto;

import com.switchfully.eurderproject.infrastructure.api.dto.AddressDTO;

import java.util.Objects;

public class CustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDTO address;
    private String phoneNumber;

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

    public AddressDTO getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerDTO setId(String id) {
        this.id = id;
        return this;
    }

    public CustomerDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerDTO setAddress(AddressDTO address) {
        this.address = address;
        return this;
    }

    public CustomerDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
