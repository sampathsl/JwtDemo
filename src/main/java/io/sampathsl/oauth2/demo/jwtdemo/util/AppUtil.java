package io.sampathsl.oauth2.demo.jwtdemo.util;

import io.sampathsl.oauth2.demo.jwtdemo.dto.RegistrationRequestDto;
import io.sampathsl.oauth2.demo.jwtdemo.enums.Role;
import io.sampathsl.oauth2.demo.jwtdemo.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public final class AppUtil {

  private final PasswordEncoder passwordEncoder;

  /**
   * Transforms a given registration request DTO into an AppUser entity.
   *
   * @param registrationRequestDto the given DTO containing registration information, such as
   *     username and password
   * @return an AppUser entity created from the registration request information
   */
  public AppUser transformRegistrationRequestToUser(
      final RegistrationRequestDto registrationRequestDto) {
    return AppUser.builder()
        .username(registrationRequestDto.getUserName())
        .password(passwordEncoder.encode(registrationRequestDto.getPassword()))
        .role(
            Role.valueOf(
                !CollectionUtils.isEmpty(registrationRequestDto.getRoles())
                    ? registrationRequestDto.getRoles().get(0)
                    : Role.USER.getLabel()))
        .build();
  }
}
