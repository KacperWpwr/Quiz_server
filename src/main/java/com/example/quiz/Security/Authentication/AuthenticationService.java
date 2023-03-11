package com.example.quiz.Security.Authentication;

import com.example.quiz.Security.Authentication.DTO.*;
import com.example.quiz.Security.Authentication.Exceptions.EmailTakenException;
import com.example.quiz.Security.Authentication.Exceptions.InvalidLoginException;
import com.example.quiz.Security.Authentication.Exceptions.LoginTakenException;
import com.example.quiz.Security.Authentication.Exceptions.PasswordMissmatchException;
import com.example.quiz.Security.JWT.JwtService;
import com.example.quiz.User.DTO.UserAccountInfoDTO;
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

    public EmailChangeResult changePassword(EmailChangeRequest request) {
        if(user_service.emailExists(request.new_email())) throw new EmailTakenException();//I <3 U
        User user = user_service.loadUserByUsername(request.username());
        if(user==null) throw new InvalidLoginException();
        user.setEmail(request.new_email());
        user = user_service.saveUser(user);
        return EmailChangeResult.builder().email(user.getEmail()).build();
    }

    public LoginChangeResult changeLogin(LoginChangeRequest request) {
        if(user_service.loginExists(request.new_username())) throw new LoginTakenException();
        User user = user_service.loadUserByUsername(request.username());
        if(user==null) throw new InvalidLoginException();
        user.setLogin(request.new_username());
        user = user_service.saveUser(user);
        String token = jwt_service.generateToken(user);
        return LoginChangeResult.builder()
                .username(user.getUsername())
                .token(token)
                .build();


    }
}
