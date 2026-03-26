package com.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

public class AccountTest {

    @Test
    public void constructorWithUsernameAndPassword_setsFieldsCorrectly() {
        Account account = new Account("user1", "pass1");
        assertNotNull(account.getAccountID());
        assertNull(account.getFirstName());
        assertNull(account.getLastName());
        assertNull(account.getEmail());
        assertEquals("user1", account.getUsername());
        assertEquals("pass1", account.getPassword());
        assertEquals(Role.STUDENT, account.getRole());
    }

    @Test
    public void constructorWithAllFields_setsAllFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "user2", "pass2", "First", "Last", "email@test.com", Role.EDITOR);
        assertEquals(id, account.getAccountID());
        assertEquals("First", account.getFirstName());
        assertEquals("Last", account.getLastName());
        assertEquals("email@test.com", account.getEmail());
        assertEquals("user2", account.getUsername());
        assertEquals("pass2", account.getPassword());
        assertEquals(Role.EDITOR, account.getRole());
    }

    @Test
    public void editUser_validInput_updatesFieldsAndReturnsTrue() {
        Account account = new Account("user3", "pass3");
        boolean result = account.editUser("NewFirst", "NewLast", "new@email.com", "newuser", "newpass");
        assertTrue(result);
        assertEquals("NewFirst", account.getFirstName());
        assertEquals("NewLast", account.getLastName());
        assertEquals("new@email.com", account.getEmail());
        assertEquals("newuser", account.getUsername());
        assertEquals("newpass", account.getPassword());
    }

    @Test
    public void editUser_invalidUsername_returnsFalseAndDoesNotUpdate() {
        Account account = new Account("user4", "pass4");
        boolean result = account.editUser("First", "Last", "email", "", "pass");
        assertFalse(result);
    }

    @Test
    public void editUser_invalidPassword_returnsFalseAndDoesNotUpdate() {
        Account account = new Account("user5", "pass5");
        boolean result = account.editUser("First", "Last", "email", "user", "");
        assertFalse(result);
    }

    // Note: login() static method requires AccountList and integration, so not tested here directly.
}
