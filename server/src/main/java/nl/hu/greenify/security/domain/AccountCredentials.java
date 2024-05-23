package nl.hu.greenify.security.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record AccountCredentials(String email, Collection<? extends GrantedAuthority> authorities) {}
