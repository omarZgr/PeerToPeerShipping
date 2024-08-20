package com.application.front.request.user.sender;

import com.application.helper.StringToDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
public class ConfirmMeetingRequest {

    @NotNull(message = "meetId is mandatory")
    private long meetId ;

    @NotEmpty(message = "location is mandatory")
    @NotBlank(message = "location is mandatory")
    private String location ;

    @NotNull(message = "dateMeeting is mandatory")
    @NotEmpty(message = "dateMeeting is mandatory")
    @NotBlank(message = "dateMeeting is mandatory")
    private String dateMeeting ;

}
