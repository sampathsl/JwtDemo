package io.sampathsl.oauth2.demo.jwtdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
  @Autowired private MockMvc mockMvc;

  @Test
  public void testBasePage() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
  }

  @Test
  public void testHomePage() throws Exception {
    mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"));
  }

  @Test
  public void testLoginPage() throws Exception {
    mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
  }

  @Test
  public void testUnauthorizedPage() throws Exception {
    mockMvc
        .perform(get("/access-denied"))
        .andExpect(status().isOk())
        .andExpect(view().name("unauthorized"));
  }
}
