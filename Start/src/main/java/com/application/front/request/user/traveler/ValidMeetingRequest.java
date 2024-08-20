package com.application.front.request.user.traveler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class ValidMeetingRequest {

    @NotNull(message = "description is mandatory")
    private long meetId ;

 //   @NotNull(message = "image is mandatory")
    private MultipartFile image ;
}
