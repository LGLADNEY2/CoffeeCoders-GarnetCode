package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class AccountList {
    private static AccountList accountList;
    private ArrayList<Account> accounts;


    private AccountList() {
        accounts = DataLoader.getAccounts();
    }

    public static AccountList getInstance() {
        if(accountList == null)
            accountList = new AccountList();
        return accountList;
    }

    public boolean getHasUser(String userName) {
        for (Account account : accounts) {
            if (account.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }
    
    public Account getAccount(String userName, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(userName) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public boolean addUser(String firstName, String lastName, String username, String password) {
        if (getHasUser(username)) {
            return false;
        }
        accounts.add(new Account(username, password)); //firstname and lastname aren't a part of constructor, needs to be fixed
        return true;
    }

    // generates a unique id for a new user
    public UUID makeID() {
        UUID id = UUID.randomUUID();
        for (Account account : accounts) {
            if (account.getAccountID().equals(id)) {
                return makeID();
            }
        }
        return id;
    }

    public boolean resetPassword(String userName, String password) {
        return true;
    }

    public boolean saveUsers() {
        DataWriter.saveAccounts(accounts);
        return true;
    }
}
