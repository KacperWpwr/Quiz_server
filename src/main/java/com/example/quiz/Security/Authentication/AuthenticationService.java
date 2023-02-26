package com.example.quiz.Security.Authentication;

import com.example.quiz.Security.Authentication.DTO.AuthenticationRequest;
import com.example.quiz.Security.Authentication.DTO.AuthenticationResponse;
import com.example.quiz.Security.Authentication.DTO.RegistrationResponse;
import com.example.quiz.Security.Authentication.DTO.RegistrationResult;
import com.example.quiz.Security.JWT.JwtService;
import com.example.quiz.User.Role;
import com.example.quiz.User.User;
import com.example.quiz.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService user_service;
    private final PasswordEncoder password_encoder;
    private final JwtService jwt_service;

    private final AuthenticationManager authentication_manager;

    public RegistrationResult registerUser(RegistrationResponse request) {
        boolean login_conflict = user_service.loginExists(request.login());
        boolean email_conflict = user_service.emailExists(request.email());

        if(login_conflict){
            System.out.println("Login");
            return new RegistrationResult(false,Optional.empty());
        }

        if(email_conflict){
            System.out.println("Email");
            return new RegistrationResult(false,Optional.empty());
        }

        if (!request.password().equals(request.match_password())){
            return new RegistrationResult(false,Optional.empty());
        }

        User new_user = User.builder()
                .login(request.login())
                .password(password_encoder.encode(request.password()))
                .email(request.email())
                .is_enabled(true)
                .is_blocked(false)
                .role(Role.USER)
                .build();
        user_service.reqisterNewUser(new_user);


        return new RegistrationResult(true, Optional.of(jwt_service.generateToken(new_user)));
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authentication_manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );

        User user = user_service.loadUserByUsername(request.login());


        return new AuthenticationResponse( jwt_service.generateToken(user));
    }
}
