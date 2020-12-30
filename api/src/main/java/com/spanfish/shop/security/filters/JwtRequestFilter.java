package com.spanfish.shop.security.filters;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.spanfish.shop.exception.CustomAuthenticationException;
import com.spanfish.shop.exception.RestAuthenticationEntryPoint;
import com.spanfish.shop.security.jwt.JwtProvider;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final UserDetailsService customerDetailsService;
  private final RestAuthenticationEntryPoint authenticationEntryPoint;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader(AUTHORIZATION);
    String email = null;
    String jwtToken = null;

    try {

      if (nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
        jwtToken = authorizationHeader.substring(7);
        email = jwtProvider.getUsernameFromToken(jwtToken);
      }

      if (nonNull(email) && isNull(getContext().getAuthentication())) {
        final UserDetails userDetails = customerDetailsService.loadUserByUsername(email);
        if (jwtProvider.validateToken(jwtToken, userDetails)) {
          final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          usernamePasswordAuthenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
      filterChain.doFilter(request, response);
    } catch (JwtException ex) {
      authenticationEntryPoint.commence(
          request, response, new CustomAuthenticationException(ex.getMessage()));
    }
  }
}
