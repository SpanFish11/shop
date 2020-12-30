package com.spanfish.shop.security.jwt;

import static io.jsonwebtoken.Claims.AUDIENCE;
import static io.jsonwebtoken.Claims.EXPIRATION;
import static io.jsonwebtoken.Claims.ID;
import static io.jsonwebtoken.Claims.ISSUED_AT;
import static io.jsonwebtoken.Claims.ISSUER;
import static io.jsonwebtoken.Claims.SUBJECT;
import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parserBuilder;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static io.jsonwebtoken.io.Decoders.BASE64;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.getInstance;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

import io.jsonwebtoken.Claims;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  private static final String ROLES = "roles";
  private static final String TOKEN_PREFIX = "Bearer ";
  private static final String TOKEN_TYPE = "JWT";

  private final JwtConfig jwtConfig;

  public String generateToken(final UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(ISSUER, jwtConfig.getIssuer());
    claims.put(SUBJECT, userDetails.getUsername());
    claims.put(ROLES, getEncryptedRoles(userDetails));
    claims.put(AUDIENCE, jwtConfig.getAudience());
    claims.put(EXPIRATION, generateExpirationDate());
    claims.put(ISSUED_AT, generateCurrentDate());
    claims.put(ID, randomUUID());
    return generateToken(claims);
  }

  private String generateToken(final Map<String, Object> claims) {
    return TOKEN_PREFIX
        + builder()
            .signWith(hmacShaKeyFor(BASE64.decode(jwtConfig.getSecret())), HS512)
            .setHeaderParam("typ", TOKEN_TYPE)
            .setClaims(claims)
            .compact();
  }

  // for retrieving any information from token we will need the secret key
  private Claims getAllClaimsFromToken(final String token) {
    return parserBuilder()
        .setSigningKey(hmacShaKeyFor(BASE64.decode(jwtConfig.getSecret())))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  // retrieve username from jwt token
  public String getUsernameFromToken(final String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getCreatedDateFromToken(final String token) {
    return (Date) getAllClaimsFromToken(token).get(ISSUED_AT);
  }

  // retrieve expiration date from jwt token
  public Date getExpirationDateFromToken(final String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private Date generateCurrentDate() {
    return new Date();
  }

  private Date generateExpirationDate() {
    final Calendar calendar = getInstance();
    calendar.add(MILLISECOND, jwtConfig.getExpiration());
    return calendar.getTime();
  }

  // check if the token has expired
  private Boolean isTokenExpired(final String token) {
    final Date expiration = this.getExpirationDateFromToken(token);
    return expiration.before(this.generateCurrentDate());
  }

  private Boolean isCreatedBeforeLastPasswordReset(
      final Date created, final Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  private List<String> getEncryptedRoles(final UserDetails userDetails) {
    return userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(s -> s.replace("ROLE_", ""))
        .map(String::toLowerCase)
        .collect(toList());
  }

  public Boolean canTokenBeRefreshed(final String token, final Date lastPasswordReset) {
    final Date created = this.getCreatedDateFromToken(token);
    return !(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
        && !(this.isTokenExpired(token));
  }

  public String refreshToken(final String token) {
    String refreshedToken;
    try {
      final Claims claims = this.getAllClaimsFromToken(token);
      claims.put(ISSUED_AT, this.generateCurrentDate());
      refreshedToken = this.generateToken(claims);
    } catch (Exception e) {
      refreshedToken = null;
    }
    return refreshedToken;
  }

  public Boolean validateToken(final String token, final UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return username.equals(userDetails.getUsername());
  }
}
