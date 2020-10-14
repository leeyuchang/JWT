package com.home.service;

import com.home.domain.Member;
import com.home.exception.DuplicateMemberException;
import com.home.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class MemberRegiserService {
  
  @Autowired
  private MemberRepository memberRepository;

  /**
   * Member Register
   * @param memberO
   * 
   * @throws DuplicateMemberException 
   */
  public Member regist(Member member) {
    if (memberRepository.findByUsername(member.getUsername()).isPresent()) {
      throw new DuplicateMemberException(
          String.format("username [%s] is already exist", member.getUsername()));
    }
    return memberRepository.save(member);
  }
  
}
