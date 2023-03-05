package com.example.quiz.Security.Authentication;

import com.example.quiz.Security.Authentication.DTO.AuthenticationRequest;
import com.example.quiz.Security.Authentication.DTO.AuthenticationResponse;
import com.example.quiz.Security.Authentication.DTO.RegistrationRequest;
import com.example.quiz.Security.Authentication.DTO.RegistrationResult;
import com.example.quiz.Security.Authentication.Exceptions.EmailTakenException;
import com.example.quiz.Security.Authentication.Exceptions.LoginTakenException;
import com.example.quiz.Security.Authentication.Exceptions.PasswordMissmatchException;
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

    public RegistrationResult registerUser(RegistrationRequest request) {
        boolean login_conflict = user_service.loginExists(request.login());
        boolean email_conflict = user_service.emailExists(request.email());

        if(login_conflict){
            throw new LoginTakenException();
        }

        if(email_conflict){
            throw new EmailTakenException();
        }

        if (!request.password().equals(request.match_password())){
            throw new PasswordMissmatchException();
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


        return new RegistrationResult(jwt_service.generateToken(new_user));
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
