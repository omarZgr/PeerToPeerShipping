package com.application.front.request.user.sender;

import com.application.helper.StringToFloatDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@Builder
public class ItemSenderRequest {

    @NotEmpty(message = "Nom is mandatory")
    @NotBlank(message = "Nom is mandatory")
    private String nom ;

    @NotEmpty(message = "Description is mandatory")
    @NotBlank(message = "Description is mandatory")
    private String description ;

    @NotEmpty(message = "Type is mandatory")
    @NotBlank(message = "Type is mandatory")
    private String type ;

    //  @NotNull(message = "Photo is mandatory")
    private MultipartFile photo ;

    @NotEmpty(message = "VilleDepart is mandatory")
    @NotBlank(message = "VilleDepart is mandatory")
    private String villeDepart ;

    @NotEmpty(message = "VilleDestination is mandatory")
    @NotBlank(message = "VilleDestination is mandatory")
    private String villeDestination ;


    @JsonDeserialize(using = StringToFloatDeserializer.class)
    @NotNull(message = "montant is mandatory")
    private Float montant ;
}
