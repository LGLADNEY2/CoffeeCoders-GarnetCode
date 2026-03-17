package com.model;

import java.util.UUID;

// Represents a user account in the system.
// Each account has unique ID, login credentials, and a role (student or editor).
public class Account {
    private UUID accountID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Role role;

    // Creates a new account with a unique ID and default role of STUDENT.
    public Account(String username, String password) {
        this.accountID = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = Role.STUDENT;
    }
    
    public Account(UUID accountID, String username, String password, String firstName, String lastName, String email, Role role) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public UUID getAccountID() {return accountID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Role getRole() {return role;}

   public void setFirstName(String firstName) {
    this.firstName = firstName;
   }
   public void setLastName(String lastName) {
    this.lastName = lastName;
   }
   public void setEmail(String email) {
    this.email = email;
   }
   public void setUsername(String username) {
    this.username = username;
   }
   public void setPassword(String password) {
    this.password = password; 
   }

    public boolean logout() {
        return true;
    }

    public static Account login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        return AccountList.getInstance().getAccount(username, password);
    }

    public boolean editUser(String firstName, String lastName, String email, String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        return true;
    }
}
