package com.blackcode.app_login_be.repository;

import com.blackcode.app_login_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
