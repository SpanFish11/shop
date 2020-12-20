package com.spanfish.shop.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static io.jsonwebtoken.Claims.AUDIENCE;
import static io.jsonwebtoken.Claims.EXPIRATION;
import static io.jsonwebtoken.Claims.ID;
import static io.jsonwebtoken.Claims.ISSUED_AT;
import static io.jsonwebtoken.Claims.ISSUER;
import static io.jsonwebtoken.Claims.SUBJECT;
import static java.util.Calendar.MILLISECOND;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  private static final String ROLES = "roles";
  private static final String TOKEN_PREFIX = "Bearer ";
  private static final String TOKEN_TYPE = "JWT";
  private static final String TOKEN_ISSUER = "secure-api";
  private static final String TOKEN_AUDIENCE = "secure-app";

  private final JwtConfig jwtConfig;

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(ISSUER, TOKEN_ISSUER);
    claims.put(SUBJECT, userDetails.getUsername());
    claims.put(ROLES, getEncryptedRoles(userDetails));
    claims.put(AUDIENCE, TOKEN_AUDIENCE);
    claims.put(EXPIRATION, generateExpirationDate());
    claims.put(ISSUED_AT, generateCurrentDate());
    claims.put(ID, UUID.randomUUID());
    return TOKEN_PREFIX + generateToken(claims);
  }

  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .signWith(
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret())),
            SignatureAlgorithm.HS512)
        .setHeaderParam("typ", TOKEN_TYPE)
        .setClaims(claims)
        .compact();
  }

  private Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret())))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String getUsernameFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  public Date getCreatedDateFromToken(String token) {
    return (Date) getClaimsFromToken(token).get(ISSUED_AT);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token).getExpiration();
  }

  private Date generateCurrentDate() {
    return new Date();
  }

  private Date generateExpirationDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(MILLISECOND, jwtConfig.getExpiration());
    return calendar.getTime();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = this.getExpirationDateFromToken(token);
    return expiration.before(this.generateCurrentDate());
  }

  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  private List<String> getEncryptedRoles(UserDetails userDetails) {
    return userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(s -> s.replace("ROLE_", ""))
        .map(String::toLowerCase)
        .collect(Collectors.toList());
  }

  public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
    final Date created = this.getCreatedDateFromToken(token);
    return !(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
        && !(this.isTokenExpired(token));
  }

  public String refreshToken(String token) {
    String refreshedToken;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      claims.put(ISSUED_AT, this.generateCurrentDate());
      refreshedToken = this.generateToken(claims);
    } catch (Exception e) {
      refreshedToken = null;
    }
    return refreshedToken;
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return username.equals(userDetails.getUsername());
  }
}
