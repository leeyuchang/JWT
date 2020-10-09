package com.home.domain;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyMember extends User {

  private static final long serialVersionUID = 1L;
  
  private Member member;

  public MyMember(Member member) {
    this(member.getUsername(), member.getPassword(), authority(member.getRoles()));
    this.member = member;
  }

  public MyMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }
  
  private static List<GrantedAuthority> authority(Set<MemberRole> roles) {
    return roles
        .stream()
        .map(MemberRole::name)
        .map(SimpleGrantedAuthority::new)
        .collect(toList());
  }

}
