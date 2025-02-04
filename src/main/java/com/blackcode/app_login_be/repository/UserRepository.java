package com.blackcode.app_login_be.repository;

import com.blackcode.app_login_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Boolean existsByUserName(String username);
}
