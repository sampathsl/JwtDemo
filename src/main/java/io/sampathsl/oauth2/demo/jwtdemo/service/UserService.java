package io.sampathsl.oauth2.demo.jwtdemo.service;

import io.sampathsl.oauth2.demo.jwtdemo.model.User;
import java.util.List;

public interface UserService {
  List<User> getAllUsers();
}
