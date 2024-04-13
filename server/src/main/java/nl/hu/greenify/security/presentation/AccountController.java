package nl.hu.greenify.security.presentation;

import lombok.Getter;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.presentation.dto.SurveyDto;
import nl.hu.greenify.security.application.AccountService;
import nl.hu.greenify.security.domain.Account;
import nl.hu.greenify.security.presentation.dto.AccountDto;
import nl.hu.greenify.security.presentation.dto.RegisterDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<AccountDto> register(@Validated @RequestBody RegisterDto registerDto) {
        Account account = this.accountService.register(registerDto.email, registerDto.password, registerDto.firstName, registerDto.lastName);
        return new ResponseEntity<AccountDto>(AccountDto.fromEntity(account), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Account> getAccount(@PathVariable String email) {
        Account account = this.accountService.getAccount(email);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
