package io.sampathsl.oauth2.demo.jwtdemo.model;

import io.sampathsl.oauth2.demo.jwtdemo.enums.Role;
import jakarta.persistence.*;
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
@Entity
@Table(name = "app_user")
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;

  @Enumerated(value = EnumType.STRING)
  private Role role; // Eg: ADMIN,USER
}
