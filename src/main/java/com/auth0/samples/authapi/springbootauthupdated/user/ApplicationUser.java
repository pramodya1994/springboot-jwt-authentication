package com.auth0.samples.authapi.springbootauthupdated.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ApplicationUser {

    // primary identifier of the user instance in the application
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // identify users
    private String username;
    // check the user identity
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
