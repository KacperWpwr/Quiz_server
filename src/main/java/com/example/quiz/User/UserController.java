package com.example.quiz.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user/demo")
public class UserController {
    @GetMapping("/gethello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Hello");
    }
}
