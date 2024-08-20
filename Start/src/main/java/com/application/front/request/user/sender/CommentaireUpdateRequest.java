package com.application.front.request.user.sender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentaireUpdateRequest {

    @NotNull(message = "meetId is mandatory")
    private long commentId ;

    @NotEmpty(message = "comment is mandatory")
    @NotBlank(message = "comment is mandatory")
    private String comment ;

    @Size(min = 0,max = 10,message = "rate should be between 0 and 8")
    private int rate ;
}
