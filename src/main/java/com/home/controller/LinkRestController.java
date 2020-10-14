package com.home.controller;

import java.time.LocalDateTime;
import javax.validation.Valid;
import com.home.config.JwtTokenUtil;
import com.home.domain.Link;
import com.home.domain.LoginForm;
import com.home.domain.SignupDto;
import com.home.exception.IdPasswordNotMatchingException;
import com.home.exception.LinkNotFoundException;
import com.home.repository.LinkRepository;
import com.home.repository.MemberRepository;
import com.home.service.MemberRegiserService;
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
import lombok.extern.java.Log;

@Log
@CrossOrigin(origins = "*")
@RestController
public class LinkRestController {

  @Autowired
  private JwtTokenUtil jwt;

  @Autowired
  private LinkRepository linkRepo;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberRegiserService memberRegiserService;

  @GetMapping("/")
  public String health() {
    return "I'm health";
  }

  @GetMapping("/list")
  public ResponseEntity<Page<Link>> list(
      @PageableDefault(page = 0, size = 3, sort = {"createDate"}) Pageable pageable) {
    log.warning(pageable.getPageNumber() + "★");

    return ResponseEntity.ok(linkRepo.findAll(pageable));
  }

  @PostMapping("/links")
  public ResponseEntity<?> regist(@RequestBody @Validated Link linkForm,
      UriComponentsBuilder uriBuilder) {
    var newLink = new Link(null, linkForm.getTitle(), linkForm.getUrl(), LocalDateTime.now());
    var saved = linkRepo.save(newLink);
    var uri = uriBuilder.path("/regist/{id}}").buildAndExpand(saved.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/links/{id}")
  public ResponseEntity<Link> getLinkbyId(@PathVariable String id) {
    var body = linkRepo.findById(id).orElseThrow(() -> new LinkNotFoundException(""));
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
  public ResponseEntity<?> editLink(@PathVariable String id,
      @RequestBody @Validated Link linkForm) {
    var newLink = new Link(id, linkForm.getTitle(), linkForm.getUrl(), linkForm.getCreateDate());
    try {
      linkRepo.save(newLink);
    } catch (Exception e) {
      throw new IllegalArgumentException("存在しないID" + id);
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping("/signup")
  public ResponseEntity<Response> signup(@RequestBody @Valid SignupDto signup) {
    var member = memberRegiserService.regist(signup.toEntity());
    var response = new Response(jwt.generateToken(member.getUsername()));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody @Valid LoginForm login) {
    var ERR_MSG = "IDと暗証番号が一致ししません。";
    var member = memberRepository.findByUsername(login.getUsername())
        .orElseThrow(() -> new IdPasswordNotMatchingException(ERR_MSG));

    if (!member.matchPassword(login.getPassword())) {
      throw new IdPasswordNotMatchingException(ERR_MSG);
    }
    var newJwtToken = jwt.generateToken(member.getUsername());
    return ResponseEntity.ok(new Response(newJwtToken));
  }

  // @ExceptionHandler(MethodArgumentNotValidException.class)
  // public ResponseEntity<?> methodArgumentExceptionHandler(MethodArgumentNotValidException ex) {
  //   ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "");
  //   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  // }
}
