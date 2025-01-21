package com.blackcode.app_login_be.dto;

import com.blackcode.app_login_be.model.Role;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {

    private String userId;

    private String userFullName;

    private String userName;

    private String userPassword;

    private String userRole;
}
