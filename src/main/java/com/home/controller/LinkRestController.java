package com.home.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.home.config.JwtTokenUtil;
import com.home.domain.Link;
import com.home.domain.LoginForm;
import com.home.domain.SignupDto;
import com.home.exception.LinkNotFoundException;
import com.home.exception.MemberNotFoundException;
import com.home.exception.PasswordNotFoundException;
import com.home.exception.UserAlreadyExistException;
import com.home.repository.LinkRepository;
import com.home.repository.MemberRepository;

import lombok.extern.java.Log;

@Log
@CrossOrigin(origins="*")
@RestController
public class LinkRestController {
  
  @Autowired
  private JwtTokenUtil jwt;

  @Autowired
  private LinkRepository linkRepo;
  
  @Autowired
  private MemberRepository memberRepository;
  
  @GetMapping("/")
  public String health( ) {
    return "I'm health";
  }
  
  @GetMapping("/list")
  public ResponseEntity<Page<Link>> list(
      @PageableDefault(page = 0, size = 3, sort = { "createDate" }) Pageable pageable) {
    log.warning(pageable.getPageNumber() + "★");

    return ResponseEntity.ok(linkRepo.findAll(pageable));
  }

  @PostMapping("/signup")
  public ResponseEntity<Response> signup(@RequestBody @Validated SignupDto signup) {
    
    if(memberRepository.existsById(signup.getUsername())) {
      throw new UserAlreadyExistException("Already exist username" + signup.getUsername());
    }
    var member = memberRepository.save(signup.toEntity());
    var response = new Response(jwt.generateToken(member.getUsername()));
    return ResponseEntity.ok(response);
  }
  
  @PostMapping("/links")
  public ResponseEntity<?> regist(@RequestBody @Validated Link linkForm, UriComponentsBuilder uriBuilder) {
    var newLink = new Link(null, linkForm.getTitle(), linkForm.getUrl(), LocalDateTime.now());
    var saved = linkRepo.save(newLink);
    var uri = uriBuilder.path("/regist/{id}}").buildAndExpand(saved.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }
  
  @GetMapping("/links/{id}")
  public ResponseEntity<Link> getLinkbyId(@PathVariable String id) {
    var body = linkRepo.findById(id).orElseThrow(()->new LinkNotFoundException(""));
    return ResponseEntity.ok(body);
  }
  
  @DeleteMapping("/links/{id}")
  public ResponseEntity<?> removeLinkById(@PathVariable String id) {
    log.warning("DELETE : " + id);
    try {
      linkRepo.deleteById(id);
    } catch (Exception e) {
      throw new IllegalArgumentException("存在しないID" + id);
    }
    return ResponseEntity.noContent().build();
  }
  
  @PutMapping("/links/{id}")
  public ResponseEntity<?> editLink(@PathVariable String id, @RequestBody @Validated Link linkForm) {
    var newLink = new Link(id, linkForm.getTitle(), linkForm.getUrl(), linkForm.getCreateDate());
    try {
      linkRepo.save(newLink);
    } catch (Exception e) {
      throw new IllegalArgumentException("存在しないID" + id);
    }
    return ResponseEntity.ok().build();
  }
  
  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody @Validated LoginForm login) {
   var member = memberRepository.findByUsername(login.getUsername()).orElseThrow(()->new MemberNotFoundException(login.getUsername()));
   
   if(!member.matchPassword(login.getPassword())) {
     throw new PasswordNotFoundException("password error");
   }
  var newJwtToken = jwt.generateToken(member.getUsername());
  return ResponseEntity.ok(new Response(newJwtToken));
  }
}
