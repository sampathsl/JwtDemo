package io.sampathsl.oauth2.demo.jwtdemo.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.sampathsl.oauth2.demo.jwtdemo.model.Comment;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureWebTestClient
public class CommentServiceImplTest {
  @MockBean private CommentService commentService;

  @Test
  void testGetAllCommentsHandlesEmptyLists() {
    Mockito.when(commentService.getAllComments()).thenReturn(Collections.emptyList());
    final List<Comment> result = commentService.getAllComments();
    assertThat(result).isEmpty();
  }

  @Test
  void testGetAllCommentsMapsCommentsCorrectly() {
    Comment comment = new Comment();
    comment.setPostId(1);
    final List<Comment> expectedComments = Arrays.asList(comment);
    Mockito.when(commentService.getAllComments()).thenReturn(expectedComments);
    final List<Comment> result = commentService.getAllComments();
    assertThat(result).containsExactly(comment);
  }
}
