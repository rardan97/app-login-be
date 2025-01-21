package com.blackcode.app_login_be.controller;

import com.blackcode.app_login_be.dto.*;
import com.blackcode.app_login_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationRes> authRegistration(@RequestBody RegistrationReq registrationReq){
        UserReq userReq = new UserReq();
        userReq.setUserPassword(passwordEncoder.encode(registrationReq.getUserPassword()));
        UserRes userRes = userService.createUser(userReq);
        RegistrationRes registrationRes = new RegistrationRes();
        registrationRes.setStatus("success");
        registrationRes.setMessage("data berhasil registrasi");
        return new ResponseEntity<>(registrationRes, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> authLogin(@RequestBody LoginReq request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserPassword()));

        if (authentication.isAuthenticated()) {
            LoginRes loginRes = new LoginRes();
            loginRes.setStatus("success");
            loginRes.setMessage("success");
            return new ResponseEntity<>(loginRes, HttpStatus.OK);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginRes> authLogout(){
        return null;
    }
}
