package com.example.quiz.User;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    public boolean emailExists(String email){
        return userRepository.userExistEmail(email);
    }
    public boolean loginExists(String login){
        return userRepository.userExistLogin(login);
    }

    public void reqisterNewUser(User new_user) {

        userRepository.save(new_user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loadUserByUsername(username);
    }
}
