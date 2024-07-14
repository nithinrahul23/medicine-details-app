package com.capstone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    private String userName;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String role;


    public User() {

    }

    public User(String userName, String firstName, String lastName, String emailId, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
    }
}
