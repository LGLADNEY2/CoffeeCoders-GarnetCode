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
        return new ArrayList<>(accounts);
    }

    // Adds a new account to the list if the username is not already taken, returns true if successful.
    public boolean addAccount(String firstName, String lastName, String email, String password) {
        return addAccount(firstName, lastName, email, password, Role.STUDENT);
    }   
    public boolean addAccount(String firstName, String lastName, String email, String username, String password, Role role) {
        if (hasAccount(username)) {
            return false;
        }

        UUID accountID = makeID();
        
        if (role == Role.EDITOR) {
            accounts.add(new Editor (accountID, firstName, lastName, email, username, password, Role.EDITOR,
                1, new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>()));
        } else {
            accounts.add(new Student(accountID, firstName, lastName, email, username, password, Role.STUDENT,
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),  new ArrayList<>()));
        }
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
    public boolean saveAccount() {
        return DataWriter.saveAccounts();
    }

    // Deletes the account with the specified username from the list.
    public boolean deleteAccount(String userName) {
        for (int i = 0; i < accounts.size(); i++) {
        if (accounts.get(i).getUsername().equalsIgnoreCase(userName)) {
            accounts.remove(i);
            }
        }
        return false;
    }
}
