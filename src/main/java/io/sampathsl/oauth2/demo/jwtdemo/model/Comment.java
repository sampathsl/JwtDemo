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
public class Comment {
  private int id;

  @JsonProperty("post_id")
  private int postId;

  private String name;
  private String email;
  private String body;
}
