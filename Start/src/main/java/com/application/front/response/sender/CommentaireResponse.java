package com.application.front.response.sender;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireResponse {

    private long commentId ;
    private LocalDateTime  createdDate ;
    private LocalDateTime  lastModifiedDate ;
    private String createdBy ;
    private String commentaire ;
}
