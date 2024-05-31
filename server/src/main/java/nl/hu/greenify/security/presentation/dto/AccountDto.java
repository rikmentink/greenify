package nl.hu.greenify.security.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.presentation.dto.PersonDto;
import nl.hu.greenify.security.domain.Account;
import nl.hu.greenify.security.domain.AccountCredentials;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountDto {
    private final String email;

    private final List<GrantedAuthority> authorities;

    private final PersonDto person;

    public AccountDto(String email, List<GrantedAuthority> authorities, PersonDto person) {
        this.email = email;
        this.authorities = authorities != null ? Collections.unmodifiableList(authorities) : Collections.emptyList();
        this.person = person;
    }

    public static AccountDto fromEntity(Account account) {
        return new AccountDto(account.getUsername(), account.getAuthorities() != null
                ? new ArrayList<>(account.getAuthorities())
                : Collections.emptyList(), PersonDto.fromEntity(account.getPerson()));
    }
}
