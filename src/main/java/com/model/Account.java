package com.model;

import java.util.UUID;

/**
 * Represents a user account in the system.
 * Each account has a unique ID, login credentials, and a role (student or editor).
 * @author Coffee Coders
 */
public class Account {
    private UUID accountID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Role role;

    /**
     * Creates a new account with a unique ID and default role of STUDENT.
     * @param username the username
     * @param password the password
     */
    public Account(String username, String password) {
        this.accountID = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = Role.STUDENT;
    }
    
    /**
     * Creates an account using all account details.
     * @param accountID the account ID
     * @param username the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email
     * @param role the role
     */
    public Account(UUID accountID, String username, String password, String firstName, String lastName, String email, Role role) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Gets the account ID.
     * @return the account ID
     */
    public UUID getAccountID() {return accountID;}

    /**
     * Gets the first name.
     * @return the first name
     */
    public String getFirstName() {return firstName;}

    /**
     * Gets the last name.
     * @return the last name
     */
    public String getLastName() {return lastName;}

    /**
     * Gets the email.
     * @return the email
     */
    public String getEmail() {return email;}

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {return username;}

    /**
     * Gets the password.
     * @return the password
     */
    public String getPassword() {return password;}

    /**
     * Gets the role.
     * @return the role
     */
    public Role getRole() {return role;}

   /**
    * Sets the first name.
    * @param firstName the new first name
    */
   public void setFirstName(String firstName) {
    this.firstName = firstName;
   }

   /**
    * Sets the last name.
    * @param lastName the new last name
    */
   public void setLastName(String lastName) {
    this.lastName = lastName;
   }

   /**
    * Sets the email.
    * @param email the new email
    */
   public void setEmail(String email) {
    this.email = email;
   }

   /**
    * Sets the username.
    * @param username the new username
    */
   public void setUsername(String username) {
    this.username = username;
   }

   /**
    * Sets the password.
    * @param password the new password
    */
   public void setPassword(String password) {
    this.password = password; 
   }

   /**
    * Sets the role.
    * @param role the new role
    */
   public void setRole(Role role) {
    this.role = role;
   }

    /**
     * Logs in with a username and password.
     * @param username the account username
     * @param password the account password
     * @return the matching account, or null if input is invalid
     */
    public static Account login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        return AccountList.getInstance().getAccount(username, password);
    }

    /**
     * Updates this account's user details.
     * @param firstName the new first name
     * @param lastName the new last name
     * @param email the new email
     * @param username the new username
     * @param password the new password
     * @return true if username and password are valid, otherwise false
     */
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
