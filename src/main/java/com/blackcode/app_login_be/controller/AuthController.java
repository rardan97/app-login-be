package com.blackcode.app_login_be.controller;

import com.blackcode.app_login_be.dto.LoginRes;
import com.blackcode.app_login_be.dto.RegistrationRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/registration")
    public ResponseEntity<RegistrationRes> authRegistration(){
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> authLogin(){
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginRes> authLogout(){
        return null;
    }
}
