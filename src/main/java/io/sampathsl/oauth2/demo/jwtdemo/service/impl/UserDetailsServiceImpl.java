package io.sampathsl.oauth2.demo.jwtdemo.service.impl;

import java.util.Optional;

import io.sampathsl.oauth2.demo.jwtdemo.enums.Role;
import io.sampathsl.oauth2.demo.jwtdemo.model.AppUser;
import io.sampathsl.oauth2.demo.jwtdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository repository;

  /**
   * Loads the user details for a given username from the repository.
   *
   * @param username the given username of the user to be loaded
   * @return the given UserDetails object containing user information including username, password,
   *     and roles
   * @throws UsernameNotFoundException if a user with the given username is not found
   */
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    Optional<AppUser> user = repository.findByUsername(username);
    if (user.isPresent()) {
      final AppUser userObj = user.get();
      return org.springframework.security.core.userdetails.User.builder()
          .username(userObj.getUsername())
          .password(userObj.getPassword())
          .roles(getRoles(userObj))
          .build();
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }

  /**
   * Retrieves the roles of the given user.
   *
   * @param user the given user whose roles need to be fetched
   * @return an array of role labels for the given user
   */
  private String[] getRoles(AppUser user) {
    if (user.getRole() == null) {
      return new String[] {Role.USER.getLabel()};
    }
    return new String[] {user.getRole().getLabel()};
  }
}
