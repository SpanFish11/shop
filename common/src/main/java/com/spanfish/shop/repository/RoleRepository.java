package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findRoleByRoleName(String roleName);
}
