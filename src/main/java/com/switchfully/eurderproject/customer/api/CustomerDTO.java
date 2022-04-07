package com.switchfully.eurderproject.customer.api;

public class CustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
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

    public String getAddress() {
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

    public CustomerDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public CustomerDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
