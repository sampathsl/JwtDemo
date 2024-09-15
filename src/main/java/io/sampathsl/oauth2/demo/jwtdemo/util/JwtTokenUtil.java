package io.sampathsl.oauth2.demo.jwtdemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenUtil {
  @Setter
  @Value("${jwt.secret.key}")
  private String base64SecretKey;

  private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 1;

  private Key secretKey;

  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(base64SecretKey);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Extracts the username from the JWT token.
   *
   * @param token the given JWT token from which the username is to be extracted
   * @return the username extracted from the token
   */
  public String extractUsername(final String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts the expiration date from the JWT token.
   *
   * @param token the given JWT token from which the expiration date is to be extracted
   * @return the expiration date extracted from the token
   */
  public Date extractExpiration(final String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts a specific claim from a provided JWT token using the given claims resolver function.
   *
   * @param token the given JWT token from which the claim is to be extracted
   * @param claimsResolver the given function to extract a specific claim from the claims
   * @return the value of the claim extracted using the claims resolver function
   */
  public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the given JWT token.
   *
   * @param token the given JWT token from which the claims are to be extracted
   * @return the claims extracted from the token
   */
  private Claims extractAllClaims(final String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }

  /**
   * Checks if the JWT token is expired.
   *
   * @param token the given JWT token to be checked for expiration
   * @return true if the token is expired, false otherwise
   */
  private Boolean isTokenExpired(final String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Generates a JWT token for the provided user details.
   *
   * @param userDetails the given user details for which the token is to be generated
   * @return the generated JWT token
   */
  public String generateToken(final UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  /**
   * Creates a JWT token with the given claims and subject.
   *
   * @param claims the given claims to be included in the token
   * @param subject the given subject for whom the token is created
   * @return the generated JWT token
   */
  private String createToken(final Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
        .signWith(secretKey)
        .compact();
  }

  /**
   * Validates the provided JWT token by checking that the username extracted from the token matches
   * the username from the provided user details, and that the token is not expired.
   *
   * @param token the given JWT token to be validated
   * @param userDetails the given user details to be matched against the token
   * @return true if the token is valid, false otherwise
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * Generates a JWT token for the authenticated user.
   *
   * @param authentication the given authentication object containing user details
   * @return the generated JWT token as a string
   */
  public String generateToken(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }
}
