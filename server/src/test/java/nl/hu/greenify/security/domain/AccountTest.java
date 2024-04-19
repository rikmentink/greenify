package nl.hu.greenify.security.domain;

import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.security.domain.enums.AccountRoles;
import nl.hu.greenify.security.domain.exceptions.AccountHasRoleAlreadyException;
import nl.hu.greenify.security.domain.exceptions.AccountWithoutPersonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {

    private Person person;

    @BeforeEach
    void setup() {
        person = new Person("John", "Doe", "john@gmail.com");
    }

    @Test
    @DisplayName("Test creating an account")
    void createAccount() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        assertEquals(person, account.getPerson());
    }

    @Test
    @DisplayName("Test creating an account with an empty person")
    void createAccountNullPerson() {
        assertThrows(AccountWithoutPersonException.class, () -> Account.createAccount("john@gmail.com", "password", null));
    }

    @Test
    @DisplayName("Tests if creating account sets the default role correctly")
    void createAccountDefaultRoleCorrectly() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        assertTrue(account.getRoles().contains(AccountRoles.ROLE_USER));
    }

    @Test
    @DisplayName("Tests if creating account sets the default role incorrectly")
    void createAccountDefaultRoleIncorrectly() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        assertFalse(account.getRoles().isEmpty());
    }


    @Test
    @DisplayName("Test adding a role to an account correctly")
    void addRoleCorrectly() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        account.addRole(AccountRoles.ROLE_MANAGER);

        assertTrue(account.getRoles().contains(AccountRoles.ROLE_MANAGER));
    }

    @Test
    @DisplayName("Test adding a role to an account incorrect")
    void addRoleIncorrectly() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        account.addRole(AccountRoles.ROLE_MANAGER);

        assertFalse(account.getRoles().contains(AccountRoles.ROLE_VUMEDEWERKER));
    }

    @Test
    @DisplayName("Test adding a role to an account that already has the role")
    void addRoleAlreadyExists() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        assertThrows(AccountHasRoleAlreadyException.class, () -> account.addRole(AccountRoles.ROLE_USER));
    }

    @Test
    @DisplayName("Test removing a role from an account correctly")
    void removeRoleCorrectly() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        account.removeRole(AccountRoles.ROLE_USER);

        assertTrue(account.getRoles().isEmpty());
    }

    @Test
    @DisplayName("Test removing a role from an account correctly")
    void removeRoleIncorrectly() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        account.addRole(AccountRoles.ROLE_MANAGER);
        account.removeRole(AccountRoles.ROLE_MANAGER);

        assertFalse(account.getRoles().contains(AccountRoles.ROLE_MANAGER));
    }

    @Test
    @DisplayName("Test removing a role from an account that doesn't have the role")
    void removeRoleDoesntExist() {
        Account account = Account.createAccount("john@gmail.com", "password", person);
        assertThrows(AccountHasRoleAlreadyException.class, () -> account.removeRole(AccountRoles.ROLE_VUMEDEWERKER));
    }

}
