package com.blackcode.app_login_be.security.service;

import com.blackcode.app_login_be.execption.TokenRefreshException;
import com.blackcode.app_login_be.model.RefreshToken;
import com.blackcode.app_login_be.model.User;
import com.blackcode.app_login_be.model.UserToken;
import com.blackcode.app_login_be.repository.RefreshTokenRepository;
import com.blackcode.app_login_be.repository.UserRepository;
import com.blackcode.app_login_be.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${blackcode.app.jwtRefreshExpirationMs}")
    private int refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenService userTokenService;



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String jwt, Long userId){


        RefreshToken refreshToken = null;
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(userId);

        if (existingToken.isPresent()) {
            // Data sudah ada, Anda bisa update atau memberikan response
//            throw new DataIntegrityViolationException("Token untuk user_id " + userId + " sudah ada.");
            refreshToken = new RefreshToken();
            refreshToken.setId(existingToken.get().getId());
            refreshToken.setUser(existingToken.get().getUser());
            refreshToken.setExpiryDate(existingToken.get().getExpiryDate());
            refreshToken.setToken(existingToken.get().getToken());


        }else{
            User dataUser = userRepository.findById(userId).get();
            refreshToken = new RefreshToken();
            refreshToken.setUser(dataUser);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken = refreshTokenRepository.save(refreshToken);
        }


        userTokenService.processUserTokenAdd(userId, jwt);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId){
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

}
