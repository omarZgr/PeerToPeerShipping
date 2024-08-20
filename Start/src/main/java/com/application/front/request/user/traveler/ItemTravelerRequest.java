package com.application.front.request.user.traveler;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemTravelerRequest {

    @NotEmpty(message = "VilleDepart is mandatory")
    @NotBlank(message = "VilleDepart is mandatory")
    private String villeDepart ;

    @NotEmpty(message = "VilleDestination is mandatory")
    @NotBlank(message = "VilleDestination is mandatory")
    private String villeDestination ;

}
