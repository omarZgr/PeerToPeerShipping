package com.application.front.request.user.sender;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReclamtionRequest {

    @NotNull(message = "userTravelerId is mandatory")
    private long userTravelerId ;

    @NotEmpty(message = "reclamation is mandatory")
    @NotBlank(message = "reclamation is mandatory")
    private String reclamation ;
}
