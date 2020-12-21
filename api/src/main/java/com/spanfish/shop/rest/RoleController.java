package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Role;
import com.spanfish.shop.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleRepository roleRepository;

  @Secured({"ROLE_ADMIN"})
  @GetMapping
  public ResponseEntity<List<Role>> findAll() {

    List<Role> roles = roleRepository.findAll();
    if (roles.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(roles, HttpStatus.OK);
  }
}
