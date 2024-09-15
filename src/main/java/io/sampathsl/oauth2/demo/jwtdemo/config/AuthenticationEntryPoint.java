package io.sampathsl.oauth2.demo.jwtdemo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationEntryPoint
    implements org.springframework.security.web.AuthenticationEntryPoint {

  /**
   * Handles an authentication error by sending an unauthorized error response.
   *
   * @param request the given request during which the authentication exception occurred
   * @param response the given response to be used to send the error response
   * @param authException the given authentication exception that triggered this method
   * @throws IOException if an input or output error occurs while sending the error response
   */
  @Override
  public void commence(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final org.springframework.security.core.AuthenticationException authException)
      throws IOException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
