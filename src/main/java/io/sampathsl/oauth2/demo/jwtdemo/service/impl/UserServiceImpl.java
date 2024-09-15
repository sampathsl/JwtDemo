package io.sampathsl.oauth2.demo.jwtdemo.service.impl;

import io.sampathsl.oauth2.demo.jwtdemo.model.User;
import io.sampathsl.oauth2.demo.jwtdemo.service.UserService;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

  private final WebClient webClient;

  @Autowired
  public UserServiceImpl(final WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://gorest.co.in/public").build();
  }

  /**
   * Retrieves a list of all users from an external service. Utilizes WebClient to make a GET
   * request to "/v2/users" endpoint.
   *
   * @return a list of User objects, or an empty list if an error occurs.
   */
  @Override
  public List<User> getAllUsers() {
    try {
      return this.webClient
          .get()
          .uri("/v2/users")
          .retrieve()
          .bodyToFlux(User.class)
          .collectList()
          .block();
    } catch (Exception e) {
      log.error("An error occurred while getting all users", e);
      return new ArrayList<>();
    }
  }
}
