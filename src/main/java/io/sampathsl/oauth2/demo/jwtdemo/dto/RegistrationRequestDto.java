package io.sampathsl.oauth2.demo.jwtdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationRequestDto {
  private static final long serialVersionUID = 16554564546541765L;

  @NotBlank(message = "error.username.required")
  @NotNull(message = "error.username.required")
  @JsonProperty("username")
  String userName;

  @NotBlank(message = "error.password.required")
  @NotNull(message = "error.password.required")
  @JsonProperty("password")
  String password;

  @NotNull(message = "error.roles.required")
  @NotBlank(message = "error.roles.required")
  @JsonProperty("roles")
  List<String> roles;
}
