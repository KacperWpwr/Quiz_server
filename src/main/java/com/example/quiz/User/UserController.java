package com.example.quiz.User;

import com.example.quiz.User.DTO.*;
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
    @GetMapping("/get/quiz/page-number/{username}")
    public ResponseEntity<UserQuizPageNumberDTO> getPageNumber(@PathVariable String username){
        return new ResponseEntity<>(user_service.getPageNumber(username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get/quiz/page-content/user={username}/page={page_num}")
    public ResponseEntity<UserPageContentDTO> getPageContent(@PathVariable(name = "username") String username
            , @PathVariable(name = "page_num") Integer page_num){
        return new ResponseEntity(user_service.getPageContext(username,page_num),HttpStatusCode.valueOf(200));
    }
}
