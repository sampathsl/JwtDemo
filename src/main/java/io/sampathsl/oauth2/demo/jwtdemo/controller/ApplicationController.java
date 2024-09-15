package io.sampathsl.oauth2.demo.jwtdemo.controller;

import io.sampathsl.oauth2.demo.jwtdemo.dto.RegistrationRequestDto;
import io.sampathsl.oauth2.demo.jwtdemo.dto.LoginRequestDto;
import io.sampathsl.oauth2.demo.jwtdemo.model.AppUser;
import io.sampathsl.oauth2.demo.jwtdemo.repository.UserRepository;
import io.sampathsl.oauth2.demo.jwtdemo.util.AppUtil;
import io.sampathsl.oauth2.demo.jwtdemo.util.AuthUtil;
import io.sampathsl.oauth2.demo.jwtdemo.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class ApplicationController {

  private final AppUtil appUtil;
  private final AuthUtil authUtil;
  private final UserDetailsService userDetailsService;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserRepository userRepository;

  @PostMapping("/register/user")
  public ResponseEntity<?> registerUser(
      @RequestBody final RegistrationRequestDto registrationRequestDto) {
    final Optional<AppUser> user =
        userRepository.findByUsername(registrationRequestDto.getUserName());
    if (user.isEmpty()) {
      final AppUser appUser = appUtil.transformRegistrationRequestToUser(registrationRequestDto);
      userRepository.save(appUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(appUser);
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }
  }

  @PostMapping("/user-login")
  public String authenticateAndGetToken(@Valid @RequestBody final LoginRequestDto loginRequestDto)
      throws Exception {
    final Authentication authentication =
        authUtil.authenticate(loginRequestDto.getUserName(), loginRequestDto.getPassword());
    if (authentication.isAuthenticated()) {
      return jwtTokenUtil.generateToken(
          userDetailsService.loadUserByUsername(loginRequestDto.getUserName()));
    } else {
      throw new UsernameNotFoundException("Invalid credentials");
    }
  }
}
