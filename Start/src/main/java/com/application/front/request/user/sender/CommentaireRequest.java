package com.application.front.request.user.sender;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CommentaireRequest {

    @NotNull(message = "meetId is mandatory")
    private long userTravelerId ;

    @NotEmpty(message = "Commentaire is mandatory")
    @NotBlank(message = "Commentaire is mandatory")
    private String commentaire ;

    @Size(min = 0,max = 10,message = "rate should be between 0 and 8")
    private int rate ;
}
