package com.application.front.request.user.sender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
public class UpdateProfileRequest {

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email ;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password ;

    @NotEmpty(message = "tel is mandatory")
    @NotBlank(message = "tel is mandatory")
    private String tel ;

  //  @NotNull(message = "photo is mandatory")
    private MultipartFile photo ;

}
