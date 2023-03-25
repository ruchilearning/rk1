package com.rk1.repository;

public class User {

    private final String firstName;
    private final String surname;

    public User(String firstName, String surname) {
        super();
        this.firstName = firstName;
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }
}
