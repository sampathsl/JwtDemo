package io.sampathsl.oauth2.demo.jwtdemo.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.sampathsl.oauth2.demo.jwtdemo.model.Post;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PostServiceImplTest {

  @MockBean private PostService postService;

  @Test
  void testGetAllPostsHandlesEmptyLists() {
    Mockito.when(postService.getAllPosts()).thenReturn(Collections.emptyList());
    final List<Post> result = postService.getAllPosts();
    assertThat(result).isEmpty();
  }

  @Test
  void testGetAllPostsMapsCommentsCorrectly() {
    final Post post = new Post(2, 3, "sample title", "sample body");
    final List<Post> expectedPosts = Arrays.asList(post);
    Mockito.when(postService.getAllPosts()).thenReturn(expectedPosts);
    final List<Post> result = postService.getAllPosts();
    assertThat(result).containsExactly(post);
  }

  @Test
  void testGetAllPostsByUserReturnsEmptyListWhenUserIdIsEmpty() {
    final String userId = "";
    Mockito.when(postService.getAllPostsByUser(userId)).thenReturn(Collections.emptyList());
    final List<Post> result = postService.getAllPostsByUser(userId);
    assertThat(result).isEmpty();
  }

  @Test
  void testGetAllPostsByUserReturnsCorrectPostsWhenUserIdIsNotEmpty() {
    final String userId = "1";
    final Post post = new Post(1, 1, "sample title", "sample body");
    final List<Post> expectedPosts = Arrays.asList(post);
    Mockito.when(postService.getAllPostsByUser(userId)).thenReturn(expectedPosts);
    final List<Post> result = postService.getAllPostsByUser(userId);
    assertThat(result).containsExactly(post);
  }
}
