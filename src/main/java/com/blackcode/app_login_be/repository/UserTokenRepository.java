package com.blackcode.app_login_be.repository;

import com.blackcode.app_login_be.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByToken(String token);
    Optional<UserToken> findByUserId(Long userId);
    Optional<UserToken> findByUserIdAndToken(Long userId, String token);
    void deleteByToken(String token);
}
