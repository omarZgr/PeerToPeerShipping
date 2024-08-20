package com.application.controller;

import com.application.front.request.auth.AuthenticationRequest;
import com.application.service.AuthenticationService;
import com.application.front.request.auth.ConfirmRequest;
import com.application.front.request.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService ;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest)
    {
        return  ResponseEntity.ok(authenticationService.login(authenticationRequest)) ;
    }

    @PostMapping(value = "/register",consumes = "multipart/form-data")
    public ResponseEntity<?> register(@ModelAttribute @Valid RegisterRequest registerRequest)
    {
        authenticationService.register(registerRequest) ;
        return ResponseEntity.status(HttpStatus.CREATED).build() ;
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmationAccount(@RequestBody @Valid ConfirmRequest confirmRequest)
    {
        authenticationService.confirm(confirmRequest) ;
        return ResponseEntity.status(HttpStatus.OK).build() ;
    }
}
