package com.example.quiz.User;

import com.example.quiz.Security.Authentication.Exceptions.InvalidLoginException;
import com.example.quiz.User.DTO.UserAccountInfoDTO;
import com.example.quiz.User.DTO.UserDescriptionChangeRequest;
import com.example.quiz.User.DTO.UserDescriptionDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository user_repository;
    public boolean emailExists(String email){
        return user_repository.userExistEmail(email);
    }
    public boolean loginExists(String login){
        return user_repository.userExistLogin(login);
    }

    public void reqisterNewUser(User new_user) {

        user_repository.save(new_user);
    }
    public User saveUser(User user){
        return user_repository.save(user);
    }

    @Override
    public User loadUserByUsername(String username)  {
        return user_repository.loadUserByUsername(username);
    }

    public UserAccountInfoDTO getAccountInfo(String username) {
        User user = user_repository.loadUserByUsername(username);
        if(user==null) throw new InvalidLoginException();

        return UserAccountInfoDTO.builder()
                .login(username)
                .email(user.getEmail())
                .description(user.getDescription())
                .build();
    }

    public UserDescriptionDTO changeDescription(UserDescriptionChangeRequest request) {
        User user = user_repository.loadUserByUsername(request.username());
        if(user==null) throw new InvalidLoginException();
        user.setDescription(request.new_description());
        user_repository.save(user);

        return UserDescriptionDTO.builder()
                .description(user.getDescription())
                .build();

    }
}
