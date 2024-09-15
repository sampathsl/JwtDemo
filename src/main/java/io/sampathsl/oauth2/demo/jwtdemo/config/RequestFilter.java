package io.sampathsl.oauth2.demo.jwtdemo.config;

import io.sampathsl.oauth2.demo.jwtdemo.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Log4j2
public class RequestFilter extends OncePerRequestFilter {

  @Lazy private final UserDetailsService userDetailsService;

  private final JwtTokenUtil tokenUtil;

  /**
   * Processes the incoming HTTP requests to authenticate JWT tokens and set the Spring Security
   * context.
   *
   * @param request the given HTTP request to be processed
   * @param response the given HTTP response to be modified
   * @param chain the given filter chain to pass the request and response to the next filter
   * @throws ServletException if an error occurs during filtering
   * @throws IOException if an I/O error occurs during filtering
   */
  @Override
  protected void doFilterInternal(
      final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

      jwtToken = requestTokenHeader.substring(7);

      try {
        username = tokenUtil.extractUsername(jwtToken);
      } catch (Exception e) {
        logger.error("Unable to get Bearer Token or the Bearer Token has been expired");
      }

    } else {
      logger.warn("JWT Token does not begin with the Bearer Token String");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

      if (tokenUtil.validateToken(jwtToken, userDetails)) {

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    chain.doFilter(request, response);
  }
}
