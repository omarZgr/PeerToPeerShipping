package com.application.controller;

import com.application.common.PageResponse;
import com.application.front.request.user.sender.*;
import com.application.front.response.sender.AllAskResponse;
import com.application.front.response.sender.AllItemsResponse;
import com.application.service.UserSenderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/sender")
@Tag(name = "UserSender")
public class UserSenderController {

    private final UserSenderService userSenderService  ;



    @PostMapping(value = "/offre",consumes = "multipart/form-data")
    public ResponseEntity sender(
            @ModelAttribute @Valid ItemSenderRequest itemSenderRequest,
            Authentication connectedUser)
    {
        userSenderService.sender(itemSenderRequest,connectedUser) ;
        return ResponseEntity.status(HttpStatus.CREATED).body("") ;
    }

    @GetMapping("/items")
    public ResponseEntity<PageResponse<AllItemsResponse>> getAllItmes(
            @RequestParam(name = "page",defaultValue = "0",required = false)int page,
            @RequestParam(name = "size",defaultValue = "2",required = false)int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(userSenderService.getAllItmes(page,size,connectedUser)) ;

    }

    @GetMapping("/asks")
    public ResponseEntity<PageResponse<AllAskResponse> > getAllAsks(
            @RequestParam(name = "page",defaultValue = "0",required = false)int page,
            @RequestParam(name = "size",defaultValue = "2",required = false)int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(userSenderService.getAllAsks(page,size,connectedUser)) ;
    }

    @GetMapping("/asks/enable")
    public ResponseEntity<PageResponse<AllAskResponse> > getAllEnableAsks(
            @RequestParam(name = "page",defaultValue = "0",required = false)int page,
            @RequestParam(name = "size",defaultValue = "2",required = false)int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(userSenderService.getAllEnableAsks(page,size,connectedUser)) ;
    }

    @PostMapping(value = "/asks/enable/confirmation",consumes = "multipart/form-data")
    public ResponseEntity<?> confirmMeeting(
            @ModelAttribute @Valid ConfirmMeetingRequest confirmMeetingRequest,
            Authentication connectedUser) throws ParseException {

        userSenderService.confirmMeeting(confirmMeetingRequest,connectedUser) ;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/meetingDoneSender")
    public ResponseEntity<?> meetingDone_Sender(@RequestParam long meetId, Authentication connectedUser)
    {
        userSenderService.meetingDone_Sender(meetId,connectedUser) ;
        return ResponseEntity.ok("OK") ;
    }

    @PostMapping("/confirmation")
    public ResponseEntity<?> confirmShipping_Sender(@ModelAttribute @Valid  ConfirmSenderShippingRequest confirmationRequest,
                                                    Authentication authentication)
    {

        userSenderService.confirmShipping_Sender(confirmationRequest,authentication)  ;
        return ResponseEntity.ok("GOOD") ;
    }

    @PostMapping("/confirmation/commentaire")
    public ResponseEntity<?> commenter(@RequestBody @Valid CommentaireRequest commentaireRequest, Authentication authentication)
    {
        userSenderService.commenter(commentaireRequest,authentication) ;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/history/commentaire")
    public ResponseEntity<?> update(@RequestBody @Valid CommentaireUpdateRequest commentaireUpdateRequest, Authentication authentication)
    {
        userSenderService.updateCommenataire(commentaireUpdateRequest,authentication) ;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/history/reclamation")
    public ResponseEntity<?> reclamation(@RequestBody @Valid  ReclamtionRequest reclamtionRequest, Authentication authentication)
    {
        userSenderService.reclamation(reclamtionRequest,authentication) ;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@ModelAttribute @Valid  UpdateProfileRequest updateProfileRequest, Authentication authentication)
    {
        userSenderService.updateProfile(updateProfileRequest,authentication) ;

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping("/desactive")
    public ResponseEntity<?> desactiveAccount(Authentication authentication)
    {
        userSenderService.desactiveAccount(authentication) ;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
