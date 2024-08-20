package com.application.front.request.user.traveler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfirmTravelerShippingRequest {

    @NotEmpty(message = "itemId is mandatory")
    @NotBlank(message = "itemId is mandatory")
    private long itemId ;

    @NotEmpty(message = "description is mandatory")
    @NotBlank(message = "description is mandatory")
    private String description ;
}
