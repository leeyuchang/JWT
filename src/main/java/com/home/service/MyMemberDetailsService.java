package com.home.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.home.domain.MyMember;
import com.home.repository.MemberRepository;

@Service
public class MyMemberDetailsService implements UserDetailsService {

  @Autowired
  MemberRepository memberRepo;

  private Map<String, MyMember> myMemberCache = new HashMap<>();

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return myMemberCache.computeIfAbsent(username, this::myMember);
  }

  private MyMember myMember(String username) {
    var member = memberRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("no username such as " + username));
    return new MyMember(member);
  }
  
}
