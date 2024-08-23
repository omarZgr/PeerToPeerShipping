package com.application.front.response.sender;

import com.application.entity.CommentaireEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllAskResponse {

    private long meetId;
    private long itemId;
    private String imageItem ;

    private LocalDateTime dateAsked ;

    private String fullName ;
    private String email ;
    private String tel ;

    private long userTravelerId ;

    private String imageTraveler ;

    private int rate ;
    private List<CommentaireResponse> commentaireResponseList ;
}
