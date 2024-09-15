package io.sampathsl.oauth2.demo.jwtdemo.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthUtilTest {

  @Autowired private AuthUtil authUtil;
  @MockBean private AuthenticationManager authenticationManager;

  @Test
  void authenticate_ValidCredentials_ShouldPassAuthentication() throws Exception {
    final Authentication authExpected =
        new UsernamePasswordAuthenticationToken("correctUser", "correctPassword");
    when(authenticationManager.authenticate(any())).thenReturn(authExpected);
    final Authentication authActual = authUtil.authenticate("correctUser", "correctPassword");
    assertEquals(authExpected, authActual);
  }

  @Test
  void authenticate_InvalidCredentials_ShouldThrowException() {
    when(authenticationManager.authenticate(any()))
        .thenThrow(new BadCredentialsException("Bad credentials"));
    final Exception exception =
        assertThrows(
            Exception.class,
            () -> {
              authUtil.authenticate("wrongUser", "wrongPassword");
            });
    final String expectedMessage = "INVALID_CREDENTIALS";
    final String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }
}
