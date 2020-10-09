package com.home.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.home.domain.MyMember;
import com.home.service.MyMemberDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired private MyMemberDetailsService jwtUserDetailsService;
  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");
    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        System.out.println("JWT Token has expired");
      }
    } else {
      logger.warn("JWT Token does not begin with Bearer String");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      MyMember userDetails = (MyMember)jwtUserDetailsService.loadUserByUsername(username);
      if (jwtTokenUtil.validateToken(jwtToken, userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken authenticationToken = createUsernamePasswordAuthenticationToken(userDetails);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(UserDetails userDetails) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,  userDetails.getAuthorities());
    return usernamePasswordAuthenticationToken;
  }
}
