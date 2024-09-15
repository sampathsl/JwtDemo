package io.sampathsl.oauth2.demo.jwtdemo.service;

import io.sampathsl.oauth2.demo.jwtdemo.model.Comment;

import java.util.List;

public interface CommentService {
  List<Comment> getAllComments();

  List<Comment> getAllCommentsByPost(String postId);
}
