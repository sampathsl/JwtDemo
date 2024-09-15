package io.sampathsl.oauth2.demo.jwtdemo.controller;

import io.sampathsl.oauth2.demo.jwtdemo.model.User;
import io.sampathsl.oauth2.demo.jwtdemo.service.UserService;
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
public class UserControllerTest {
  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  private List<User> mockUsers;

  @BeforeEach
  void setUp() {
    mockUsers =
        Arrays.asList(
            new User(1, "User One", "user1@example.com", "Male", "active"),
            new User(2, "User Two", "user2@example.com", "Male", "active"));
  }

  @Test
  void testGetAllUsers() {
    when(userService.getAllUsers()).thenReturn(mockUsers);
    final ResponseEntity<List<User>> response = userController.getAllUsers();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockUsers, response.getBody());
    verify(userService, times(1)).getAllUsers();
  }
}
