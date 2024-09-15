package io.sampathsl.oauth2.demo.jwtdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  private AuthenticationEntryPoint authenticationEntryPoint;

  private RequestFilter requestFilter;

  @Lazy
  @Autowired
  public SecurityConfig(
      AuthenticationEntryPoint authenticationEntryPoint, RequestFilter requestFilter) {
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.requestFilter = requestFilter;
  }

  /**
   * Configures the security filter chain for the application, setting up CSRF, authorization rules,
   * exception handling, session management, and JWT request filtering.
   *
   * @param httpSecurity the given {@link HttpSecurity} object to be configured
   * @return the configured {@link SecurityFilterChain}
   * @throws Exception in case of any configuration errors
   */
  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            registry -> {
              registry
                  .requestMatchers(
                      "/",
                      "/home/**",
                      "/login",
                      "/api/v1/register/user/**",
                      "/api/v1/user-login/**",
                      "/access-denied/**")
                  .permitAll();
              registry.requestMatchers("/api/v1/users/**").authenticated();
              registry.requestMatchers("/api/v1/posts/**").authenticated();
              registry.requestMatchers("/api/v1/comments/**").authenticated();
              registry.anyRequest().authenticated(); // Any other request must be authenticated
            })
        .exceptionHandling(
            exceptionHandling -> {
              exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
              exceptionHandling.accessDeniedPage("/access-denied"); // Add access denied page
            })
        .sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      final AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
