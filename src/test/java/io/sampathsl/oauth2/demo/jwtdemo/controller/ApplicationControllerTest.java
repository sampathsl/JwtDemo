package io.sampathsl.oauth2.demo.jwtdemo.controller;

import io.sampathsl.oauth2.demo.jwtdemo.dto.LoginRequestDto;
import io.sampathsl.oauth2.demo.jwtdemo.repository.UserRepository;
import io.sampathsl.oauth2.demo.jwtdemo.util.AppUtil;
import io.sampathsl.oauth2.demo.jwtdemo.util.AuthUtil;
import io.sampathsl.oauth2.demo.jwtdemo.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicationControllerTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "pass";
  private ApplicationController accessController;
  @Mock private AppUtil appUtil;
  @Mock private AuthUtil authUtil;
  @Mock private UserDetailsService userDetailsService;
  @Mock private JwtTokenUtil jwtUtil;
  @Mock private Authentication authentication;
  @Mock private UserRepository userRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
    accessController =
        new ApplicationController(appUtil, authUtil, userDetailsService, jwtUtil, userRepository);
  }

  @Test
  void shouldAuthenticateAndGetToken() throws Exception {
    final LoginRequestDto loginRequest = new LoginRequestDto(USERNAME, PASSWORD);
    final User user = new User(USERNAME, PASSWORD, Collections.emptyList());
    given(userDetailsService.loadUserByUsername(USERNAME)).willReturn(user);
    given(authUtil.authenticate(USERNAME, PASSWORD)).willReturn(authentication);
    when(authentication.isAuthenticated()).thenReturn(true);
    given(jwtUtil.generateToken(user)).willReturn("sample token");
    String token = accessController.authenticateAndGetToken(loginRequest);
    assertEquals("sample token", token);
  }

  @Test
  void shouldThrowUsernameNotFoundException() throws Exception {
    final LoginRequestDto loginRequest = new LoginRequestDto(USERNAME, PASSWORD);
    given(authUtil.authenticate(USERNAME, PASSWORD)).willReturn(authentication);
    when(authentication.isAuthenticated()).thenReturn(false);
    final Exception exception =
        assertThrows(
            UsernameNotFoundException.class,
            () -> accessController.authenticateAndGetToken(loginRequest));
    assertEquals("Invalid credentials", exception.getMessage());
  }
}
