package io.sampathsl.oauth2.demo.jwtdemo.dto;

import java.io.Serializable;
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
public class JwtResponseDto implements Serializable {
  private static final long serialVersionUID = 45645646545464564L;
  private String token;
}
