package com.home.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.domain.Link;

public interface LinkRepository extends MongoRepository<Link, String>{
  
  Page<Link> findByTitle(String title, Pageable pageable);

}
