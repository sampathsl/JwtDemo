package io.sampathsl.oauth2.demo.jwtdemo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class AuthUtil {
  private final AuthenticationManager authenticationManager;

  /**
   * This Authenticates a user based on the provided username and password.
   *
   * @param username the given username of the user attempting to authenticate
   * @param password the given password of the user attempting to authenticate
   * @return the Authentication object if the user is successfully authenticated
   * @throws Exception if the user is disabled or the credentials are invalid
   */
  public Authentication authenticate(final String username, final String password)
      throws Exception {

    try {
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
