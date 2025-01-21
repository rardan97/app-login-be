package com.blackcode.app_login_be.service.imp;

import com.blackcode.app_login_be.dto.UserReq;
import com.blackcode.app_login_be.dto.UserRes;
import com.blackcode.app_login_be.repository.UserRepository;
import com.blackcode.app_login_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserRes> getListUserAll() {
        return null;
    }

    @Override
    public UserRes getUserById(Long userId) {
        return null;
    }

    @Override
    public UserRes createUser(UserReq UserReq) {
        return null;
    }

    @Override
    public UserRes updateUser(Long userId, UserReq UserReq) {
        return null;
    }

    @Override
    public String deleteUser(Long userId) {
        return null;
    }
}
