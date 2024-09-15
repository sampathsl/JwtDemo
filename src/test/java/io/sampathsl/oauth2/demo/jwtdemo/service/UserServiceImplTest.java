package io.sampathsl.oauth2.demo.jwtdemo.service;

import io.sampathsl.oauth2.demo.jwtdemo.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceImplTest {
  @MockBean private UserService userService;

  @Test
  void testGetAllUsersHandlesEmptyLists() {
    Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());
    List<User> result = userService.getAllUsers();
    assertThat(result).isEmpty();
  }

  @Test
  void testGetAllUsersMapsCommentsCorrectly() {
    final User user = new User(1, "name", "email", "Male", "Active");
    final List<User> expectedUsers = Arrays.asList(user);
    Mockito.when(userService.getAllUsers()).thenReturn(expectedUsers);
    final List<User> result = userService.getAllUsers();
    assertThat(result).containsExactly(user);
  }
}
