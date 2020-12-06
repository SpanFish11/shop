package com.spanfish.shop.entity;

public enum SystemRoles /*implements GrantedAuthority*/ {
  /*	ADMIN(Code.ADMIN),
  USER(Code.USER);

  private final String authority;

  Role(String authority) {
  	this.authority = authority;
  }

  @Override
  public String getAuthority() {
  	return authority;
  }

  public class Code {
  	public static final String ADMIN = "ROLE_ADMIN";
  	public static final String USER = "ROLE_USER";
  }*/
  ROLE_USER,
  ROLE_ADMIN,
  ROLE_MANAGER
}

// аннотация для предоставления прав доступа.
// @Secured(Roles.Code.ADMIN)
// https://stackoverflow.com/questions/8835818/handling-roles-when-authenticated-to-active-directory-with-spring-security-3-1/8944991#8944991
// https://stackoverflow.com/questions/37615034/spring-security-spring-boot-how-to-set-roles-for-users/50533455
// https://medium.com/@xoor/jwt-authentication-service-44658409e12c

// private Collection<? extends GrantedAuthority> getAuthorities(
//  Collection<Role> roles) {
//    List<GrantedAuthority> authorities
//      = new ArrayList<>();
//    for (Role role: roles) {
//        authorities.add(new SimpleGrantedAuthority(role.getName()));
//        role.getPrivileges().stream()
//         .map(p -> new SimpleGrantedAuthority(p.getName()))
//         .forEach(authorities::add);
//    }
//
//    return authorities;
// }
