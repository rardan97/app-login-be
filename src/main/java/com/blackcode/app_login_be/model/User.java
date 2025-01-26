package com.blackcode.app_login_be.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long userId;

    private String userFullName;

    private String userName;

    private String userPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role userRole;

    public User(String userFullName, String username, String encode, String password) {
    }

    public User(String userFullName, String userName, String userPassword, Role userRole) {
        this.userFullName = userFullName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }
}
