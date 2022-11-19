package com.rk1.repository;

import org.springframework.stereotype.Component;

@Component
public class UserDao {

    public User readUserName() {
        return new User("Joe", "White");
    }

}