package io.sampathsl.oauth2.demo.jwtdemo.service.impl;

import java.util.ArrayList;
import java.util.List;

import io.sampathsl.oauth2.demo.jwtdemo.model.Post;
import io.sampathsl.oauth2.demo.jwtdemo.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.util.StringUtils;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

  private final WebClient webClient;

  @Autowired
  public PostServiceImpl(final WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://gorest.co.in/public").build();
  }

  /**
   * Retrieves all posts.
   *
   * @return a list of all posts; if an error occurs, an empty list is returned
   */
  @Override
  public List<Post> getAllPosts() {
    try {
      return this.webClient
          .get()
          .uri("/v2/posts")
          .retrieve()
          .bodyToFlux(Post.class)
          .collectList()
          .block();
    } catch (Exception e) {
      log.error("An error occurred while getting all posts", e);
    }
    return new ArrayList<>();
  }

  /**
   * Retrieves all posts made by a specific user.
   *
   * @param userId the given ID of the user whose posts are to be retrieved
   * @return a list of posts made by the specified user; if an error occurs or if the userId is
   *     empty, an empty list is returned
   */
  @Override
  public List<Post> getAllPostsByUser(String userId) {
    if (!StringUtils.isEmptyOrWhitespace(userId)) {
      try {
        return this.webClient
            .get()
            .uri("/v2/users/" + userId + "/posts")
            .retrieve()
            .bodyToFlux(Post.class)
            .collectList()
            .block();
      } catch (Exception e) {
        log.error("An error occurred while getting all post by the user: " + userId, e);
        return new ArrayList<>();
      }
    }
    log.warn("An error occurred while getting all post by the user due to user id is empty");
    return new ArrayList<>();
  }
}
