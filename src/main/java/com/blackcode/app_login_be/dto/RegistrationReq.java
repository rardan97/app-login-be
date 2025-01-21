package com.blackcode.app_login_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationReq {
    private String userId;

    private String userFullName;

    private String userName;

    private String userPassword;

    private String userRole;
}
