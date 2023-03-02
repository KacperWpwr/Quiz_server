package com.example.quiz;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/page")
public class PageController {
    @GetMapping("/check/login")
    public ResponseEntity<Boolean> checkLogin(){
        return new ResponseEntity<Boolean>(true,HttpStatusCode.valueOf(200));
    }
}
