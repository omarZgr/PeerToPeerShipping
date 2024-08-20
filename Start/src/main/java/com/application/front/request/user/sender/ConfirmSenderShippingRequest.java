package com.application.front.request.user.sender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
public class ConfirmSenderShippingRequest {

    @NotNull(message = "itemId is mandatory")
    private long itemId ;

    @NotEmpty(message = "description is mandatory")
    @NotBlank(message = "description is mandatory")
    private String description ;

   // @NotNull(message = "description is mandatory")
    private MultipartFile image ;

}
