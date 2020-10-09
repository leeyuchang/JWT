package com.home.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignupDto {
     
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  @NotEmpty
  private String name;
  @NotEmpty
  private String email;
   
  public Member toEntity() {
    return new Member(username, password, name, email, LocalDateTime.now(), Set.of(MemberRole.ROLE_USER));
  }
}
