package nl.hu.greenify.security.application;

import jakarta.transaction.Transactional;
import nl.hu.greenify.core.data.GreenifyRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.security.application.exceptions.AccountAlreadyExistsException;
import nl.hu.greenify.security.data.AccountRepository;
import nl.hu.greenify.security.domain.Account;
import nl.hu.greenify.security.application.exceptions.AccountNotFoundException;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final GreenifyRepository greenifyRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, GreenifyRepository greenifyRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.greenifyRepository = greenifyRepository;
    }

    public Account register(String email, String password, String firstName, String lastName) {
        // Check if the email is already in use
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new AccountAlreadyExistsException("Email is already in use");
        }

        // Encode the password
        String encodedPassword = this.passwordEncoder.encode(password);

        // Create a new person
        Person person = new Person(firstName, lastName, email);
        this.greenifyRepository.save(person);

        // Create a new user
        Account account = new Account(email, encodedPassword, person);
        this.accountRepository.save(account);

        return account;
    }

    public Account getAccount(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
