package com.example.quiz.User;

import com.example.quiz.User.DTO.UserAccountInfoDTO;
import com.example.quiz.User.DTO.UserDescriptionChangeRequest;
import com.example.quiz.User.DTO.UserDescriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService user_service;
    @GetMapping("/get/profile-info/{username}")
    public ResponseEntity<UserAccountInfoDTO> getAccountInfo(@PathVariable String username){
        return new ResponseEntity<>(user_service.getAccountInfo(username),HttpStatusCode.valueOf(200));
    }
    @PutMapping("/change/description")
    public ResponseEntity<UserDescriptionDTO> changeDescription(@RequestBody UserDescriptionChangeRequest request){
        return new ResponseEntity<>(user_service.changeDescription(request),HttpStatusCode.valueOf(200));
    }
}
