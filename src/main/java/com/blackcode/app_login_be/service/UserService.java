package com.blackcode.app_login_be.service;

import com.blackcode.app_login_be.dto.UserReq;
import com.blackcode.app_login_be.dto.UserRes;

import java.util.List;

public interface UserService {
    List<UserRes> getListUserAll();
    UserRes getUserById(Long userId);

    UserRes createUser(UserReq UserReq);

    UserRes updateUser(Long userId, UserReq UserReq);

    String deleteUser(Long userId);
}
