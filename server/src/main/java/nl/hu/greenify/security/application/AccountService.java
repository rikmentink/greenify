package nl.hu.greenify.security.application;

import jakarta.transaction.Transactional;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.security.application.exceptions.AccountAlreadyExistsException;
import nl.hu.greenify.security.data.AccountRepository;
import nl.hu.greenify.security.domain.Account;
import nl.hu.greenify.security.application.exceptions.AccountNotFoundException;


import nl.hu.greenify.security.domain.AccountCredentials;
import nl.hu.greenify.security.domain.enums.AccountRoles;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    public AccountCredentials login(String email, String password) {
        Account account = this.accountRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        if (!this.passwordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        return new AccountCredentials(account.getUsername(), account.getAuthorities());
    }

    public Account register(String email, String password, String firstName, String lastName) {
        // Check if the email is already in use
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new AccountAlreadyExistsException("Email is already in use");
        }

        // Encode the password
        String encodedPassword = this.passwordEncoder.encode(password);

        // Create a new person
        Person person = Person.createPerson(firstName, lastName, email);
        this.personRepository.save(person);

        // Create a new user
        Account account = Account.createAccount(email, encodedPassword, person);
        return this.accountRepository.save(account);
    }

    public Account addRole(String email, String role) {
        Account account = getAccountByEmail(email);
        account.addRole(AccountRoles.valueOf(role.toUpperCase()));
        return accountRepository.save(account);
    }

    public Account removeRole(String email, String role) {
        Account account = getAccountByEmail(email);
        account.removeRole(AccountRoles.valueOf(role.toUpperCase()));
        return accountRepository.save(account);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public Account getCurrentAccount() {
        AccountCredentials accountCredentials = (AccountCredentials)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return accountRepository.findByEmail(accountCredentials.email())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
