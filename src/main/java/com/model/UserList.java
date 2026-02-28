package com.model;

import java.util.ArrayList;

public class UserList {
    private ArrayList<Account> users;
    private static UserList userList;

    private UserList() {
        userList = DataLoader.getUsers();
    }

    public static UserList getInstanct() {
        if(userList == null)
            userList = new UserList();
        return userList;
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
