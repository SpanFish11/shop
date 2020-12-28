package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findRoleByRoleName(String roleName);
}
