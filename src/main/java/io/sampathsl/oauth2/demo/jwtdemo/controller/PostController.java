package io.sampathsl.oauth2.demo.jwtdemo.controller;

import java.util.List;

import io.sampathsl.oauth2.demo.jwtdemo.model.Post;
import io.sampathsl.oauth2.demo.jwtdemo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

  @GetMapping("/posts")
  public ResponseEntity<List<Post>> getAllPosts() {
    List<Post> posts = postService.getAllPosts();
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/posts/user/{user_id}")
  public ResponseEntity<List<Post>> getAllPosts(@PathVariable("user_id") String userId) {
    List<Post> posts = postService.getAllPostsByUser(userId);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }
}
