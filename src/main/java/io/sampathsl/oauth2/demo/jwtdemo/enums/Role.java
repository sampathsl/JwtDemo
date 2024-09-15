package io.sampathsl.oauth2.demo.jwtdemo.enums;

import lombok.Getter;

@Getter
public enum Role {
  ADMIN("ADMIN"),
  USER("USER");

  public final String label;

  Role(String label) {
    this.label = label;
  }
}
