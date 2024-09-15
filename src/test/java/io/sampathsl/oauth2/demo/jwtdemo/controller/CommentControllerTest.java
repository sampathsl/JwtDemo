package io.sampathsl.oauth2.demo.jwtdemo.controller;

import io.sampathsl.oauth2.demo.jwtdemo.model.Comment;
import io.sampathsl.oauth2.demo.jwtdemo.service.CommentService;
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
public class CommentControllerTest {
  @Mock private CommentService commentService;

  @InjectMocks private CommentController commentController;

  private List<Comment> mockComments;

  @BeforeEach
  void setUp() {
    mockComments =
        Arrays.asList(
            new Comment(1, 1, "Comment One", "test1@testwork.com", "ABC One"),
            new Comment(2, 2, "Comment Two", "test2@testwork.com", "ABC Two"));
  }

  @Test
  void testGetAllComments() {
    when(commentService.getAllComments()).thenReturn(mockComments);
    final ResponseEntity<List<Comment>> response = commentController.getAllComments();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockComments, response.getBody());
    verify(commentService, times(1)).getAllComments();
  }
}
