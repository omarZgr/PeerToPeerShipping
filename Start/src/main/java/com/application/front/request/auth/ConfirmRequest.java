package com.application.front.request.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfirmRequest {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not formatted")
    private String email ;

    @NotBlank(message = "Code is mandatory")
    private String codeValidation ;


}
