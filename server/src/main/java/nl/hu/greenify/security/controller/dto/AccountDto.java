package nl.hu.greenify.security.controller.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.security.domain.Account;

@Getter
public class AccountDto {
    private final String email;

    public AccountDto(String email) {
        this.email = email;
    }

    public static AccountDto fromEntity(Account account) {
        return new AccountDto(account.getUsername());
    }
}
