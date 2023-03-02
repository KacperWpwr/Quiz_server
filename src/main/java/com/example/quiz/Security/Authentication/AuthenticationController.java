package com.example.quiz.Security.Authentication;

import com.example.quiz.Security.Authentication.DTO.AuthenticationRequest;
import com.example.quiz.Security.Authentication.DTO.AuthenticationResponse;
import com.example.quiz.Security.Authentication.DTO.RegistrationRequest;
import com.example.quiz.Security.Authentication.DTO.RegistrationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/user/auth",consumes = {"application/json"})
public class AuthenticationController {

    private final AuthenticationService authentication_service;
    @PostMapping("/register")
    public ResponseEntity<RegistrationResult> registerUser(@RequestBody RegistrationRequest request){
        return new ResponseEntity<RegistrationResult>(authentication_service.registerUser(request),HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> getAuthentication(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<AuthenticationResponse>(authentication_service.authenticate(request), HttpStatusCode.valueOf(200));
    }
}
