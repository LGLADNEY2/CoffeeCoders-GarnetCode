package com.model;

import java.util.ArrayList;
import java.util.UUID;


// Manages the collection of user accounts from JSON
public class AccountList {
    private static AccountList accountList;
    private final ArrayList<Account> accounts;

// Loads accounts from JSON file, ensures only one instance of AccountList exists (singleton pattern)
    private AccountList() {
        ArrayList<Account> loaded = DataLoader.getAccounts();
        this.accounts = loaded != null ? loaded : new ArrayList<>();
    }

// Provides access to the single instance of AccountList, creating it if it doesn't exist.
    public static AccountList getInstance() {
        if(accountList == null)
            accountList = new AccountList();
        return accountList;
    }

 // Checks if an account with the given username exists in the list.   
    public boolean hasAccount(String userName) {
        if (userName == null) return false;
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(userName)) {
                return true;
            }
        }
        return false;
    }

// Retrieves an account matching the provided username and password, ignoring case for the username.    
    public Account getAccount(String userName, String password) {
        if (userName == null || password == null) return null;
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(userName) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    // Returns the list of all accounts.
    public ArrayList<Account> getAccounts() {
        return new ArrayList<>();
    }

    // Adds a new account to the list if the username is not already taken, returns true if successful.
    public boolean addAccount(String firstName, String lastName, String username, String password, String email) {
        if (hasAccount(username)) {
            return false;
        }
        Account newAccount = new Account(makeID(), username, password, firstName, lastName, email, Role.STUDENT);
        accounts.add(newAccount);
        return true;
    }

    // Generates a unique ID for a new user
    public UUID makeID() {
        while (true) {
            UUID id = UUID.randomUUID();
            boolean exists = false;
            for (Account account : accounts) {
                if (account.getAccountID().equals(id)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return id;
            }
        }
    }

    // Saves the current list of accounts to the JSON file.
    public boolean save() {
        return DataWriter.saveAccounts();
    }

    // Deletes the account with the specified username from the list.
    public boolean deleteAccount(String userName) {
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(userName)) {
                accounts.remove(account);
                return true;
            }
        }
        return false;
    }
}
