package com.example.quiz.Security.Authentication;

import com.example.quiz.Security.Authentication.DTO.*;
import com.example.quiz.User.DTO.UserAccountInfoDTO;
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

    @PutMapping("/change/email")
    public ResponseEntity<EmailChangeResult> changeEmail(@RequestBody EmailChangeRequest request){
        return new ResponseEntity<>(authentication_service.changePassword(request),HttpStatusCode.valueOf(200));
    }
    @PutMapping("/change/login")
    public ResponseEntity<LoginChangeResult> changeLogin(@RequestBody LoginChangeRequest request){
        return new ResponseEntity<>(authentication_service.changeLogin(request),HttpStatusCode.valueOf(200));
    }
}
