package nl.hu.greenify.security.presentation;

import nl.hu.greenify.security.application.AccountService;
import nl.hu.greenify.security.domain.Account;
import nl.hu.greenify.security.presentation.dto.AccountDto;
import nl.hu.greenify.security.presentation.dto.RegisterDto;

import nl.hu.greenify.security.presentation.dto.RoleDto;
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

    @PostMapping("/addrole")
    public ResponseEntity<AccountDto> addRole(@Validated @RequestBody RoleDto roleDto) {
        Account account = this.accountService.addRole(roleDto.email, roleDto.role);
        return new ResponseEntity<AccountDto>(AccountDto.fromEntity(account), HttpStatus.OK);
    }

    @PostMapping("/removerole")
    public ResponseEntity<AccountDto> removeRole(@Validated @RequestBody RoleDto roleDto) {
        Account account = this.accountService.removeRole(roleDto.email, roleDto.role);
        return new ResponseEntity<AccountDto>(AccountDto.fromEntity(account), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Account> getAccount(@PathVariable String email) {
        Account account = this.accountService.getAccountByEmail(email);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
