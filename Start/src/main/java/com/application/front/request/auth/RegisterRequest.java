package com.application.front.request.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class RegisterRequest {

    @NotEmpty(message = "Nom is mandatory")
    @NotBlank(message = "Nom is mandatory")
    private String nom ;

    @NotEmpty(message = "Prenom is mandatory")
    @NotBlank(message = "Prenom is mandatory")
    private String prenom ;


    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not formatted")
    private String email ;


    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password ;

    @NotEmpty(message = "Tel is mandatory")
    @NotBlank(message = "Tel is mandatory")
    private String tel ;


    private MultipartFile image ;

    @NotEmpty(message = "CIN is mandatory")
    @NotBlank(message = "CIN is mandatory")
    private String cin ;


}
