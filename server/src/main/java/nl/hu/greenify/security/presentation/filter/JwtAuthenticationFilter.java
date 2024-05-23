package nl.hu.greenify.security.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.hu.greenify.security.application.AccountService;
import nl.hu.greenify.security.domain.Account;
import nl.hu.greenify.security.domain.AccountCredentials;
import nl.hu.greenify.security.presentation.dto.LoginDto;

import nl.hu.greenify.security.application.exceptions.AccountNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final String secret;
    private final Integer expirationInMs;
    private final AccountService accountService;

    public JwtAuthenticationFilter(String path, String secret, Integer expirationInMs, AccountService accountService) {
        super(new AntPathRequestMatcher(path));
        this.secret = secret;
        this.expirationInMs = expirationInMs;
        this.accountService = accountService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        LoginDto login = new ObjectMapper()
                .readValue(request.getInputStream(), LoginDto.class);

        try {
            var accountCredentials = accountService.login(login.email(), login.password());

            return new UsernamePasswordAuthenticationToken(accountCredentials, null, accountCredentials.authorities());
        } catch (AuthenticationException e) {
            throw new AccountNotFoundException("Email or password is incorrect");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        AccountCredentials account = (AccountCredentials) authentication.getPrincipal();

        List<String> roles = account.authorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        byte[] signingKey = this.secret.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("greenify-api")
                .setAudience("greenify-app")
                .setSubject(account.email())
                .setExpiration(new Date(System.currentTimeMillis() + this.expirationInMs))
                .claim("rol", roles)
                .claim("email", account.email())
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }
}
