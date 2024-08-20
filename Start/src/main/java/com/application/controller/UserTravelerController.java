package com.application.controller;


import com.application.common.PageResponse;
import com.application.front.request.user.traveler.ConfirmTravelerShippingRequest;
import com.application.front.request.user.traveler.ItemTravelerRequest;
import com.application.front.request.user.traveler.ValidMeetingRequest;
import com.application.front.response.traveler.ItemTravelerResponse;
import com.application.service.UserTravelerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/traveler")
@Tag(name = "UserTraveler")
public class UserTravelerController {

    private final UserTravelerService userTravelerService ;

    @PostMapping("/service/search")
    public ResponseEntity<PageResponse<ItemTravelerResponse>> traveler(
            @RequestBody @Valid ItemTravelerRequest itemTravelerRequest,
            @RequestParam(name = "page",defaultValue = "0",required = false)int page,
            @RequestParam(name = "size",defaultValue = "2",required = false)int size)
    {
        return ResponseEntity.ok(userTravelerService.traveler(page,size,itemTravelerRequest)) ;
    }

    @GetMapping("/service/search/{itemId}")
    public ResponseEntity<?> askMeeting(
            @PathVariable("itemId") long itemId,
            Authentication connectedUser)
    {
        userTravelerService.askMeeting(itemId,connectedUser) ;
        return ResponseEntity.status(HttpStatus.OK).build() ;
    }

    @PostMapping("/meeting/valid")
    public ResponseEntity<?> meetingDone_Traveler(@ModelAttribute @Valid ValidMeetingRequest validMeetingRequest, Authentication connectedUser)
    {
        userTravelerService.meetingDone_Traveler(validMeetingRequest,connectedUser); ;
        return ResponseEntity.ok("OK") ;
    }

    @PostMapping("/confirmation/confirm")
    public ResponseEntity<?> confirmShipping_Traveler(
            @RequestBody ConfirmTravelerShippingRequest confirmTravelerShippingRequest,
            Authentication authentication)
    {

        userTravelerService.confirmShipping_Traveler(confirmTravelerShippingRequest,authentication)  ;
        return ResponseEntity.status(HttpStatus.OK).build() ;
    }


    @DeleteMapping("/desactive")
    public ResponseEntity<?> desactiveAccount(Authentication authentication)
    {
        userTravelerService.desactiveAccount(authentication) ;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
