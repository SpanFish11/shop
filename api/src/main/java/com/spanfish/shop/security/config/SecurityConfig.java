package com.spanfish.shop.security.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.spanfish.shop.security.filters.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String API = "/api/v1";
  private final UserDetailsService customerDetailsService;
  private final JwtRequestFilter jwtRequestFilter;
  private final PasswordEncoder passwordEncoder;

  @Value("${security.auth.whitelist}")
  private final String[] whitelist;

  @Autowired
  protected void configureAuthentication(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customerDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests(
            authorize ->
                authorize
                    .antMatchers(API + "/auth")
                    .permitAll()
                    .antMatchers("/actuator/**")
                    .permitAll()
                    .antMatchers(GET, API + "/categories/**")
                    .permitAll()
                    .antMatchers(API + "/accounts/**")
                    .permitAll()
                    .antMatchers(GET, API + "/manufacturers/**")
                    .permitAll()
                    .antMatchers(GET, API + "/products/**")
                    .permitAll()
                    .antMatchers(GET, API + "/subcategories/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement()
        .sessionCreationPolicy(STATELESS);
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(final WebSecurity web) {
    web.ignoring().antMatchers(whitelist);
  }
}
