package io.sampathsl.oauth2.demo.jwtdemo.model;

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
public class User {
  private int id;
  private String name;
  private String email;
  private String gender;
  private String status;
}
