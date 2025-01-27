package com.blackcode.app_login_be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {



    @GetMapping("/home")
    public ResponseEntity<?> getHomePage() {
        return ResponseEntity.ok("Welcome to the Home Page! You are successfully logged in.");
    }
}
