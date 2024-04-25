package com.example.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemSolveController {
    // 1.
    @GetMapping("/get_test")
    public String getTest() {
        return "GET";
    }

    @PostMapping("/post_test")
    public String postTest() {
        return "POST";
    }

    @PutMapping("/put_test")
    public String putTest() {
        return "PUT";
    }

    

}















