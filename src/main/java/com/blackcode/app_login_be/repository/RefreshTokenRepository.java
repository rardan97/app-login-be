package com.blackcode.app_login_be.repository;

import com.blackcode.app_login_be.model.RefreshToken;
import com.blackcode.app_login_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.id = :userId")
    Optional<RefreshToken> findByUserId(Long userId);
    @Modifying
    int deleteByUser(User user);
}
