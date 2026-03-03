package com.model;

import java.util.ArrayList;

public class UserList {
    private static UserList userList;
    private ArrayList<Account> accounts;


    private UserList() {
        userList = DataLoader.getAccounts();
    }

    public static UserList getInstance() {
        if(userList == null)
            userList = new UserList();
        return userList;
    }

    public boolean getHasUser(String userName) {
        for (Account account : accounts) {
            if (account.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }
    
    public Account getAccount(String userName) {
        for (Account account : accounts) {
            if (account.getUsername().equals(userName)) {
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
        accounts.add(new Account(firstName, lastName, username, password));
        return true;
    }

    public boolean resetPassword(String userName, String password) {
        return true;
    }

    public void saveUsers() {
        DataWriter.saveAccounts(accounts);
    }
}
