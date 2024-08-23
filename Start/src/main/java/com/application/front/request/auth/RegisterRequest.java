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

    @NotBlank(message = "Nom is mandatory")
    private String nom ;

    @NotBlank(message = "Prenom is mandatory")
    private String prenom ;


    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not formatted")
    private String email ;


    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password ;

    @NotBlank(message = "Tel is mandatory")
    private String tel ;


    private MultipartFile image ;

    @NotBlank(message = "CIN is mandatory")
    private String cin ;


}
