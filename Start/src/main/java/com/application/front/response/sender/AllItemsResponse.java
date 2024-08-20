package com.application.front.response.sender;


import com.application.enums.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AllItemsResponse {

    private long id;

    private String nom ;
    private String description ;
    private String type ;
    private String photo ;
    private String villeDepart ;
    private String villeDestination ;
    private float price ;
    private StatusItem statusItem ;
    private boolean enable ;
}
