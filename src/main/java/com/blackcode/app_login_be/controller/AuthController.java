package com.blackcode.app_login_be.controller;


import com.blackcode.app_login_be.execption.TokenRefreshException;
import com.blackcode.app_login_be.model.RefreshToken;
import com.blackcode.app_login_be.model.Role;
import com.blackcode.app_login_be.model.User;
import com.blackcode.app_login_be.model.UserToken;
import com.blackcode.app_login_be.payload.request.LoginRequest;
import com.blackcode.app_login_be.payload.request.SignupRequest;
import com.blackcode.app_login_be.payload.request.TokenRefreshRequest;
import com.blackcode.app_login_be.payload.response.JwtResponse;
import com.blackcode.app_login_be.payload.response.MessageResponse;
import com.blackcode.app_login_be.payload.response.TokenRefreshResponse;
import com.blackcode.app_login_be.repository.RoleRepository;
import com.blackcode.app_login_be.repository.UserRepository;
import com.blackcode.app_login_be.repository.UserTokenRepository;
import com.blackcode.app_login_be.security.jwt.JwtUtils;
import com.blackcode.app_login_be.security.service.RefreshTokenService;
import com.blackcode.app_login_be.security.service.UserDetailsImpl;
import com.blackcode.app_login_be.security.service.UserTokenService;
import com.blackcode.app_login_be.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    JwtUtils jwtUtils;



    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(jwt, userDetails.getUserId());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getUserId(), userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        if(userRepository.existsByUserName(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error : Username is already taken!"));
        }
        Optional<Role> roleData = roleRepository.findByRoleName(signupRequest.getRole());
        if(roleData == null || roleData.isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already in use!"));
        }

        User user = new User(signupRequest.getUserFullName(), signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()), roleData.get());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUserName());
                    userTokenService.processUserTokenRefresh(user.getUserName(), token);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));


    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Test request : "+request.getMethod());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                Long userId = userDetails.getUserId();

                String jwtToken = token.substring(7);

                Optional<UserToken> userTokenData = userTokenRepository.findByToken(jwtToken);
                if(userTokenData.isPresent()){
                    refreshTokenService.deleteByUserId(userId);
                    userTokenData.get().setIsActive(false);
                    userTokenRepository.save(userTokenData.get());

                    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
                }else{
                    System.out.println("check token not found");
                    return ResponseEntity.ok(new MessageResponse("Log out Failed!!!"));
                }
            } else {
                return ResponseEntity.ok(new MessageResponse("Authorization not null"));
            }

        }else {
            return ResponseEntity.ok(new MessageResponse("authentication not found"));
        }

    }
}
