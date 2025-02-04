package com.blackcode.app_login_be.security.service;

import com.blackcode.app_login_be.model.User;
import com.blackcode.app_login_be.model.UserToken;
import com.blackcode.app_login_be.repository.UserRepository;
import com.blackcode.app_login_be.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserTokenService {

    @Value("${blackcode.app.jwtRefreshExpirationMs}")
    private int refreshTokenDurationMs;

    @Value("${blackcode.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    UserRepository userRepository;

    public void processUserTokenAdd(Long userId, String jwt){
        Date date = new Date((new Date()).getTime() + jwtExpirationMs);
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<UserToken> userTokenData = userTokenRepository.findByUserId(userId);
        if(userTokenData.isPresent()){
            System.out.println("check jwt update : "+jwt);
            userTokenData.get().setToken(jwt);
            userTokenData.get().setIsActive(true);
            userTokenData.get().setExpiryDate(localDateTime);
            userTokenData.get().setUpdatedAt(LocalDateTime.now());
            userTokenRepository.save(userTokenData.get());
        }else{
            System.out.println("check jwt Add : "+jwt);
            UserToken userToken = new UserToken();
            userToken.setToken(jwt);
            userToken.setUserId(userId);
            userToken.setIsActive(true);
            userToken.setExpiryDate(localDateTime);
            userToken.setCreatedAt(LocalDateTime.now());
            userToken.setUpdatedAt(LocalDateTime.now());
            userTokenRepository.save(userToken);
        }
    }

    public void processUserTokenRefresh(String userName, String jwt){
        Date date = new Date((new Date()).getTime() + jwtExpirationMs);
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<User> dataUser = userRepository.findByUserName(userName);
        if(dataUser.isPresent()){
            Optional<UserToken> userTokenData = userTokenRepository.findByUserId(dataUser.get().getUserId());
            if(userTokenData.isPresent()){
                System.out.println("check jwt update"+jwt);
                userTokenData.get().setToken(jwt);
                userTokenData.get().setIsActive(true);
                userTokenData.get().setExpiryDate(localDateTime);
                userTokenData.get().setUpdatedAt(LocalDateTime.now());
                userTokenRepository.save(userTokenData.get());
            }
        }
    }
}