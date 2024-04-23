package nl.hu.greenify.security.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleDto {

    @NotBlank
    public String email;
    @NotBlank
    public String role;
}
