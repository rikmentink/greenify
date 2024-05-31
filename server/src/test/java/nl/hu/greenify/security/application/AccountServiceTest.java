package nl.hu.greenify.security.application;

import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.security.application.exceptions.AccountAlreadyExistsException;
import nl.hu.greenify.security.application.exceptions.AccountNotFoundException;
import nl.hu.greenify.security.data.AccountRepository;
import nl.hu.greenify.security.domain.Account;
import nl.hu.greenify.security.domain.AccountCredentials;
import nl.hu.greenify.security.domain.enums.AccountRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final PersonRepository greenifyRepository = mock(PersonRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final AccountService accountService = new AccountService(accountRepository, passwordEncoder, greenifyRepository);
    private Person person;
    private final List<AccountRoles> roles = List.of(AccountRoles.ROLE_USER);

    @BeforeEach
    void setup() {
        person = new Person(1L, "John", "Doe", "johndoe@gmail.com", new ArrayList<>());
    }

    @Test
    @DisplayName("get account by email")
    void getAccountByEmail() {
        Account account = Account.createAccount("johndoe@gmail.com", "password", person);
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(account));

        assertEquals(account, accountService.getAccountByEmail("johndoe@gmail.com"));
    }

    @Test
    @DisplayName("get account by email throws exception")
    void getAccountByEmailThrowsException() {
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountByEmail("johndoe@gmail.com"));
    }

    @Test
    @DisplayName("load user by username")
    void loadUserByUsername() {
        Account account = Account.createAccount("johndoe@gmail.com", "password", person);
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(account));

        assertEquals(account, accountService.loadUserByUsername("johndoe@gmail.com"));
    }

    @Test
    @DisplayName("load user by username throws exception")
    void loadUserByUsernameThrowsException() {
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername("johndoe@gmail.com"));
    }

    @Test
    @DisplayName("registering an account")
    void registeringAnAccount() {
        when(passwordEncoder.encode("password")).thenReturn("password");
        when(greenifyRepository.save(person)).thenReturn(person);

        // Register an account
        accountService.register("johndoe@gmail.com", "password", "John", "doe");

        // Verify if the account has been saved
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("registering an account throws exception when email is already in use")
    void registeringAccountThrowsExceptionWhenEmailAlreadyExists() {
        when(passwordEncoder.encode("password")).thenReturn("password");
        when(greenifyRepository.save(person)).thenReturn(person);

        Account account = Account.createAccount("johndoe@gmail.com", "password", person);
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(account));

        assertThrows(AccountAlreadyExistsException.class, () -> accountService.register("johndoe@gmail.com",
                "password", "John", "doe"));
    }

    @Test
    @DisplayName("login an account")
    void loginToAnAccount(){
        Account account = Account.createAccount("johndoe@gmail.com", "password", person);
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("password", account.getPassword())).thenReturn(true);

        // Login to the account
        AccountCredentials accountCredentials = accountService.login(account.getUsername(), account.getPassword());

        // Verify if the account has been logged in with the correct roles
        List<String> profileRoles = accountCredentials.authorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        List<String> expectedRoles = roles
                .stream()
                .map(AccountRoles::toString)
                .toList();

        assertEquals(account.getUsername(), accountCredentials.email());
        assertEquals(expectedRoles, profileRoles);
    }

    @Test
    @DisplayName("login an account throws exception when user not found")
    void loginToAnAccountThrowsExceptionWhenUserNotFound() {
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.matches("password", "password")).thenReturn(true);

        // Login to the account, should throw an exception because the user is not found
        assertThrows(BadCredentialsException.class, () -> accountService.login("johndoe@gmail.com", "password"));
    }

    @Test
    @DisplayName("add role to an account")
    void addRoleToAccount() {
        Account account = Account.createAccount("johndoe@gmail.com", "password", person);
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(account));
        // Add a role to the account
        accountService.addRole("johndoe@gmail.com", "ROLE_MANAGER");

        // Verify if the role has been added
        assertTrue(account.getRoles().contains(AccountRoles.ROLE_MANAGER));
    }

    @Test
    @DisplayName("role from an account")
    void removeRoleFromAccount() {
        Account account = Account.createAccount("johndoe@gmail.com", "password", person);
        when(accountRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(account));
        // Add a role to the account
        accountService.removeRole("johndoe@gmail.com", "ROLE_USER");

        // Verify if the role has been added
        assertTrue(account.getRoles().isEmpty());
    }

    
}
