package com.home.domain;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Document
@ToString
@NoArgsConstructor
public class Member {
    
  @Id
  private String id;
  private String username;
  private String password;
  private String name;
  private LocalDateTime regdate;
  private Set<MemberRole> roles;

  public Member(String username, String password, String name, LocalDateTime regdate, Set<MemberRole> roles) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.regdate = regdate;
    this.roles = roles;
  }
  
  public boolean matchPassword(String password) {
    return this.password.equals(password);
  }
  
}
