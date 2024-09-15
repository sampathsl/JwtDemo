package io.sampathsl.oauth2.demo.jwtdemo.service;

import io.sampathsl.oauth2.demo.jwtdemo.model.Post;
import java.util.List;

public interface PostService {
  List<Post> getAllPosts();

  List<Post> getAllPostsByUser(String userId);
}
