package io.sampathsl.oauth2.demo.jwtdemo.controller;

import java.util.List;

import io.sampathsl.oauth2.demo.jwtdemo.model.Comment;
import io.sampathsl.oauth2.demo.jwtdemo.service.CommentService;
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
public class CommentController {

  private final CommentService commentService;

  @GetMapping("/comments")
  public ResponseEntity<List<Comment>> getAllComments() {
    List<Comment> comments = commentService.getAllComments();
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @GetMapping("/comments/post/{post_id}")
  public ResponseEntity<List<Comment>> getAllPosts(@PathVariable("post_id") String postId) {
    List<Comment> comments = commentService.getAllCommentsByPost(postId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }
}
