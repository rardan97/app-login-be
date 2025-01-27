package com.blackcode.app_login_be.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long userId;
    private String userName;
    private List<String> roles;

    public JwtResponse(String token, String refreshToken, Long userId, String userName, List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
    }
}
