package com.model;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Manages the collection of user accounts loaded from JSON data.
 * @author Coffee Coders
 */
public class AccountList {
    private static AccountList accountList;
    private final ArrayList<Account> accounts;

    /**
     * Creates the account list and loads accounts from the data source.
     */
    private AccountList() {
        ArrayList<Account> loaded = DataLoader.getAccounts();
        this.accounts = loaded != null ? loaded : new ArrayList<>();
    }

    /**
     * Returns the singleton instance of AccountList.
     *
     * @return single AccountList instance
     */
    public static AccountList getInstance() {
        if(accountList == null)
            accountList = new AccountList();
        return accountList;
    }

    /**
     * Checks whether an account exists for the given username.
     *
     * @param userName username to search for
     * @return true if the username exists, otherwise false
     */
    public boolean hasAccount(String userName) {
        if (userName == null) return false;
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the account that matches the provided credentials.
     *
     * @param userName username for the account lookup
     * @param password password for the account lookup
     * @return matching account, or null if no match is found
     */
    public Account getAccount(String userName, String password) {
        if (userName == null || password == null) return null;
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(userName) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Returns a copy of all accounts.
     *
     * @return list of accounts
     */
    public ArrayList<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /**
     * Adds a new student account if the username is not already used.
     *
     * @param firstName account first name
     * @param lastName account last name
     * @param email account email
     * @param username account username
     * @param password account password
     * @return true if the account is added, otherwise false
     */
    public boolean addAccount(String firstName, String lastName, String email, String username, String password) {
        return addAccount(firstName, lastName, email, username, password, Role.STUDENT);
    }

    /**
     * Adds a new account with the given role if the username is not already used.
     *
     * @param firstName account first name
     * @param lastName account last name
     * @param email account email
     * @param username account username
     * @param password account password
     * @param role account role
     * @return true if the account is added, otherwise false
     */
    public boolean addAccount(String firstName, String lastName, String email, String username, String password, Role role) {
        if (hasAccount(username)) {
            return false;
        }        
        if (role == Role.EDITOR) {
            accounts.add(new Editor ( firstName, lastName, email, username, password, Role.EDITOR,
                1, new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>()));
        } else {
            accounts.add(new Student(firstName, lastName, email, username, password, Role.STUDENT, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),  new ArrayList<>()));
        }
        return true;
    }

    /**
     * Saves the current account list to the data file.
     *
     * @return true if save succeeds, otherwise false
     */
    public boolean saveAccount() {
        return DataWriter.saveAccounts();
    }

    /**
     * Deletes the account with the specified account ID from the list.
     *
     * @param accountID account ID to remove
        * @return result of the deletion attempt
     */
    public boolean deleteAccount(UUID accountID) {
        for (int i = 0; i < accounts.size(); i++) {
        if (accounts.get(i).getAccountID().equals(accountID)) {
            accounts.remove(i);
            }
        }
        return false;
    }
}
