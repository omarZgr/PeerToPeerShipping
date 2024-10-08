package com.application.front.request.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationRequest {

    @Email(message = "Email is not formatted")
    @NotBlank(message = "Email is mandatory")
    private String email ;

    @NotBlank(message = "Password is mandatory")
    private String password ;
}
