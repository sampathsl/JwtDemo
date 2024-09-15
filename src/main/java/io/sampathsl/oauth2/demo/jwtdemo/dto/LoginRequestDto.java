package io.sampathsl.oauth2.demo.jwtdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Valid
public class LoginRequestDto {
  private static final long serialVersionUID = 16554564546541765L;

  @NotBlank(message = "error.username.required")
  @NotNull(message = "error.username.required")
  @JsonProperty("username")
  String userName;

  @NotBlank(message = "error.password.required")
  @NotNull(message = "error.password.required")
  @JsonProperty("password")
  String password;
}
