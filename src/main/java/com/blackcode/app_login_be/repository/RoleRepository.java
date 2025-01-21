package com.blackcode.app_login_be.repository;

import com.blackcode.app_login_be.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
