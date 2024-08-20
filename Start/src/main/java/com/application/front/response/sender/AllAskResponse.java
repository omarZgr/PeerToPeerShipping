package com.application.front.response.sender;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllAskResponse {

    private long id;
    private LocalDateTime dateAsked ;
    private String fullName ;
    private String email ;
    private String tel ;
    private String imageItem ;
    private String imageTraveler ;
}
