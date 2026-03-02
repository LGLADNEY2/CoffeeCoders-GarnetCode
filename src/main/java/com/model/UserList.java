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

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getUser() {
        return null;
    }

    public boolean getHasUser(String user) {
        return true;
    }

    public boolean getHasUser(String user, String password) {
        return true;
    }

    public boolean addUser(String firstName, String lastName, String username, String password) {
        return true;
    }

    public boolean resetPassword(String user, String password) {
        return true;
    }
}
