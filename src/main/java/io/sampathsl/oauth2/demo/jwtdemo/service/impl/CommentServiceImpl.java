package io.sampathsl.oauth2.demo.jwtdemo.service.impl;

import java.util.ArrayList;
import java.util.List;

import io.sampathsl.oauth2.demo.jwtdemo.model.Comment;
import io.sampathsl.oauth2.demo.jwtdemo.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.util.StringUtils;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService {

  private final WebClient webClient;

  @Autowired
  public CommentServiceImpl(final WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://gorest.co.in/public").build();
  }

  /**
   * Retrieves all comments from the web service.
   *
   * @return a list of all comments. If an error occurs during retrieval, an empty list is returned.
   */
  @Override
  public List<Comment> getAllComments() {
    try {
      return this.webClient
          .get()
          .uri("/v2/comments")
          .retrieve()
          .bodyToFlux(Comment.class)
          .collectList()
          .block();
    } catch (Exception e) {
      log.error("An error occurred while getting all comments", e);
      return new ArrayList<>();
    }
  }

  /**
   * Retrieves all comments associated with a specific post ID.
   *
   * @param postId the given ID of the post for which to retrieve comments. Must not be null or
   *     empty.
   * @return a list of comments associated with the specified post ID. If an error occurs or the
   *     post ID is empty, returns an empty list.
   */
  @Override
  public List<Comment> getAllCommentsByPost(String postId) {
    if (!StringUtils.isEmptyOrWhitespace(postId)) {
      try {
        return this.webClient
            .get()
            .uri("/v2/posts/" + postId + "/comments")
            .retrieve()
            .bodyToFlux(Comment.class)
            .collectList()
            .block();
      } catch (Exception e) {
        log.error("An error occurred while getting all comments by the post: " + postId, e);
        return new ArrayList<>();
      }
    }
    log.warn("An error occurred while getting all comments by the post due to post id is empty");
    return new ArrayList<>();
  }
}
