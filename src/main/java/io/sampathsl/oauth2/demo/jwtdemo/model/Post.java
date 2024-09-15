package io.sampathsl.oauth2.demo.jwtdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class Post {
  private int id;

  @JsonProperty("user_id")
  private int userId;

  private String title;
  private String body;
}
