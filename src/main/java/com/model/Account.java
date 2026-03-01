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
        this.role = Role.STUDENT;
    }
    public Account(UUID accountID, String user, String password) {
        this.accountID = accountID;
        this.username = user;
        this.password = password;
        this.role = Role.STUDENT;
    }
    
    public UUID getAccountID() {return accountID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Role getRole() {return role;}

   public void setFirstName(String firstName) {

   }
   public void setLastName(String lastName) {

   }
   public void setEmail(String email) {

   }
   public void setUsername(String username) {

   }
   public void setPassword(String password) {

   }

    public boolean login() {
        return true;
    }
    public boolean logout() {
        return true;
    }    
}
