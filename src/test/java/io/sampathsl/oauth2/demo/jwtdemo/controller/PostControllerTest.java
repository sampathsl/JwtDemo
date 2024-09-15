package io.sampathsl.oauth2.demo.jwtdemo.controller;

import io.sampathsl.oauth2.demo.jwtdemo.model.Post;
import io.sampathsl.oauth2.demo.jwtdemo.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
  @Mock private PostService postService;

  @InjectMocks private PostController postController;

  private List<Post> mockPosts;

  @BeforeEach
  void setUp() {
    mockPosts =
        Arrays.asList(
            new Post(1, 1, "Post One", "Content One"), new Post(2, 2, "Post Two", "Content Two"));
  }

  @Test
  void testGetAllPosts() {
    when(postService.getAllPosts()).thenReturn(mockPosts);
    final ResponseEntity<List<Post>> response = postController.getAllPosts();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockPosts, response.getBody());
    verify(postService, times(1)).getAllPosts();
  }
}
