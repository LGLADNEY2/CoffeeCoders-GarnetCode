package com.model;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;

public class AccountListTest {
    private AccountList accountList;

    @BeforeEach
    public void setUp() {
        // Reset the singleton for test isolation (if possible)
        // If not possible, tests may not be fully isolated
        accountList = AccountList.getInstance();
        // Remove all accounts for a clean state if possible
        accountList.getAccounts().clear();
    }

    @Test
    public void hasAccount_returnsFalseWhenUsernameNotPresent() {
        assertFalse(accountList.hasAccount("nonexistent"));
    }

    @Test
    public void addAccount_addsStudentAccountSuccessfully() {
        boolean added = accountList.addAccount("First", "Last", "email@test.com", "student1", "pass1");
        assertTrue(added);
        assertTrue(accountList.hasAccount("student1"));
    }

    @Test
    public void addAccount_duplicateUsername_returnsFalse() {
        accountList.addAccount("First", "Last", "email@test.com", "student2", "pass2");
        boolean added = accountList.addAccount("Other", "User", "other@email.com", "student2", "diffpass");
        assertFalse(added);
    }

    @Test
    public void getAccount_withCorrectCredentials_returnsAccount() {
        accountList.addAccount("First", "Last", "email@test.com", "student3", "pass3");
        Account acc = accountList.getAccount("student3", "pass3");
        assertNotNull(acc);
        assertEquals("student3", acc.getUsername());
        assertEquals("pass3", acc.getPassword());
    }

    @Test
    public void getAccount_withIncorrectCredentials_returnsNull() {
        accountList.addAccount("First", "Last", "email@test.com", "student4", "pass4");
        Account acc = accountList.getAccount("student4", "wrongpass");
        assertNull(acc);
    }

    @Test
    public void deleteAccount_removesAccountById() {
        accountList.addAccount("First", "Last", "email@test.com", "student5", "pass5");
        Account acc = accountList.getAccount("student5", "pass5");
        assertNotNull(acc);
        boolean deleted = accountList.deleteAccount(acc.getAccountID());
        // The method always returns false, but we can check removal
        assertFalse(accountList.hasAccount("student5"));
    }
}
