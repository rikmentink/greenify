package nl.hu.greenify.security.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class RegisterDto {
    @NotBlank
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;
}
