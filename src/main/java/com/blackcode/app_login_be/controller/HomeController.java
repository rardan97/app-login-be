package com.blackcode.app_login_be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<?> getHomePage() {
        Map<String, String> rtn = new HashMap<>();
        rtn.put("message", "Welcome to the Home Page! You are successfully logged in.");
        return ResponseEntity.ok(rtn);
    }
}
