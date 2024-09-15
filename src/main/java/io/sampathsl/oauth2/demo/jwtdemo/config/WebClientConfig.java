package io.sampathsl.oauth2.demo.jwtdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder()
        .baseUrl("https://gorest.co.in/public")
        .defaultHeader("Content-Type", "application/json");
  }
}
