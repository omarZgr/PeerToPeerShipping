package com.application.controller;

import com.application.front.request.auth.AuthenticationRequest;
import com.application.service.AuthenticationService;
import com.application.front.request.auth.ConfirmRequest;
import com.application.front.request.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@Log4j2
public class AuthenticationController {

    private final AuthenticationService authenticationService ;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest)
    {
        return  ResponseEntity.ok(authenticationService.login(authenticationRequest)) ;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute @Valid RegisterRequest registerRequest) throws IOException {
        log.warn("registerRequest >>> "+ registerRequest.getPassword())  ;
        authenticationService.register(registerRequest) ;
        return ResponseEntity.status(HttpStatus.CREATED).build() ;
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody @Valid ConfirmRequest confirmRequest)
    {
        authenticationService.confirm(confirmRequest) ;
        return ResponseEntity.status(HttpStatus.OK).build() ;
    }
}
