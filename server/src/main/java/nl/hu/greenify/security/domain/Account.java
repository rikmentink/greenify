package nl.hu.greenify.security.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.security.domain.enums.AccountRoles;
import nl.hu.greenify.security.domain.exceptions.AccountHasRoleAlreadyException;
import nl.hu.greenify.security.domain.exceptions.AccountWithoutPersonException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Table(name = "accounts")
@Data
@Entity
public class Account implements UserDetails {

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_roles", joinColumns = @JoinColumn(name =
            "account_id"))
    @Enumerated(EnumType.STRING)
    private final Set<AccountRoles> roles = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    @OneToOne
    private Person person;

    public Account() {
    }

    public Account(String email, String password, Person person) {
        this.email = email;
        this.password = password;
        this.person = person;

        addDefaultRole();
    }

    public static Account createAccount(String email, String password, Person person) {
        if (person == null || person.getEmail() == null)
            throw new AccountWithoutPersonException("Person is required to create an account");

        return new Account(email, password, person);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return authorities;
    }

    public void addDefaultRole() {
        roles.add(AccountRoles.ROLE_USER);
    }

    public void addRole(AccountRoles role) {
        if (roles.contains(role))
            throw new AccountHasRoleAlreadyException(String.format("Account already has role %s and cannot be added again", role.name()));

        roles.add(role);
    }

    public void removeRole(AccountRoles role) {
        if (!roles.contains(role))
            throw new AccountDoesntHaveRoleException(String.format("Account doesn't have role %s , meaning it cannot be removed", role.name()));

        roles.remove(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
