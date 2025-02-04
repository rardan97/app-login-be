package com.blackcode.app_login_be.security.service;

import com.blackcode.app_login_be.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;

    @JsonIgnore
    private String userPassword;
    private final Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long userId, String username, String userPassword, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = username;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user){
        String roleName = user.getUserRole() != null ? user.getUserRole().getRoleName() : "DEFAULT";
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName));
        return new UserDetailsImpl(
                user.getUserId(),
                user.getUserName(),
                user.getUserPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userId, user.userId);
    }
}
