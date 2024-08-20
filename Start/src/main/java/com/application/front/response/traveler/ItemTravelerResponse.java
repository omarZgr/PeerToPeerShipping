package com.application.front.response.traveler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemTravelerResponse {
    private long itemId ;

    private String itemName ;
    private String itemDescription ;
    private String itemType ;
    private String itemPhoto ;
    private String itemVilleDepart ;
    private String itemVilleDestination ;
    private float itemPrice ;

}
