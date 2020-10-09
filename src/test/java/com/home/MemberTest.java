package com.home;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.home.repository.MemberRepository;

 @SpringBootTest
public class MemberTest {

   @Autowired
   MemberRepository repo;

  @Test
  void member() {
     var result = repo.findByUsername("leeyucha@msn.com");
     result.ifPresent(m -> {
     assertThat(m.getUsername()).isEqualTo("leeyucha@msn.com");
     assertThat(m.getPassword()).isEqualTo("1234");
     });
  
  }
}
