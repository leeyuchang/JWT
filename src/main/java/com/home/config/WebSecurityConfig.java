package com.home.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.home.service.MyMemberDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  MyMemberDetailsService myMemberDetailsService;

  @Autowired
  JwtRequestFilter jwtRequestFilter;

  @Autowired
  public void configureGolbal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(myMemberDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors();  // chrome등으로 javascript의 fetch를 실행하면 preflight하기 때문에 서버에서 이것을 받아 준다는 
                          // 받아주는 옵션을 켜주지 않으면 chrome은 에러로 처리함 (curl등은 괜찮음)
                          // 분리해서 맨처음에 선언하지 않으면 @CrossOrigin(origins="*")을 선언 했어도
                          // OncePerRequestFilter에서 request.getHeader("Authorization")가 null이 리턴됨
                          // 참고:https://www.baeldung.com/spring-security-cors-preflight
    
    httpSecurity
    .csrf().disable()
    .authorizeRequests()
    .antMatchers("/").permitAll()
    .antMatchers("/list").permitAll()
    .antMatchers("/signup").permitAll()
    .antMatchers("/login").permitAll()
    .anyRequest().authenticated()
    .and()
    .exceptionHandling()
    .authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
    .sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

}
