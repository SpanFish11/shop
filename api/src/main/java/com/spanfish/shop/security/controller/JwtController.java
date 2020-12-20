package com.spanfish.shop.security.controller;

import com.spanfish.shop.security.jwt.JwtProvider;
import com.spanfish.shop.security.models.AuthRequest;
import com.spanfish.shop.security.models.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class JwtController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService customerDetailsService;
  private final JwtProvider jwtProvider;

  @PostMapping
  public ResponseEntity<AuthResponse> authentication(@RequestBody AuthRequest authRequest)
      throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authRequest.getEmail(), authRequest.getPassword()));
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect email or password", e);
    }
    UserDetails customer = customerDetailsService.loadUserByUsername(authRequest.getEmail());
    String jwtToken = jwtProvider.generateToken(customer);
    return ResponseEntity.ok(AuthResponse.builder().token(jwtToken).build());
  }
}
