package com.model;

import java.util.UUID;

public abstract class Account {
    private UUID accountID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Role role;

    public Account(String user, String password) {
        this.accountID = UUID.randomUUID();
        this.username = user;
        this.password = password;
    }
    public Account(UUID accountID, String user, String password) {
        this.accountID = accountID;
        this.username = user;
        this.password = password;
    }
    
    public boolean addEmail(String user, String password) {
        return true;
    }

    public boolean login() {
        return true;
    }
    public boolean logout() {
        return true;
    }

    public boolean removeUser(String user, String password) {
        return true;
    }

    public boolean editUser(String user, String password) {
        return true;
    }

    public boolean resetPassword(String user, String password) {
        return true;
    }

    
    
}
