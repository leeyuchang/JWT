package com.home.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.domain.Member;

public interface MemberRepository extends MongoRepository<Member, String> {
  Optional<Member> findByUsername(String username);
}
