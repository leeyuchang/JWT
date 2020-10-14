package com.home.config;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.exception.ExceptionResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException
                                     ) throws IOException, ServletException {
    
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(LocalDateTime.now(), "権限無し（ログインしてください。）", "");
    
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getOutputStream(), exceptionResponse);
    } catch (Exception e) {
      throw new ServletException();
    }
  }

}
