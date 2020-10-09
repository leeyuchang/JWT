package com.home.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

  public static final long JWT_TOKEN_VALIDITY = 86400000;

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  /**
   * retrieve username from jwt token
   * 
   * @param token
   * @return username
   */
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * retrieve expiration date from jwt token
   * 
   * @param token
   * @return expiration
   */
  public Date getExpirationFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Claim
   * 
   * @param <T>
   * @param token jwt
   * @param fn    function to do
   * @return
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> fn) {
    Claims claims = getAllClaimsFromToken(token);
    return fn.apply(claims);
  }

  /**
   * retrieve claims from token by secret key
   * 
   * @param token
   * @return payload
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  /**
   * return true, if the token has expired
   * 
   * @param token
   * @return
   */
  private Boolean isTokenExpired(String token) {
    Date expiration = getExpirationFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * generate token for user
   * 
   * @param userDetails
   * @return
   */
  public String generateToken(String username) {
    var claims = new HashMap<String, Object>();
    return doGenerateToken(claims, username);
  }

  /**
   * 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID 2. Sign the JWT
   * using the HS512 algorithm and secret key. 3. According to JWS Compact
   * 
   * @param claims
   * @param subject
   * @return
   */
  private String doGenerateToken(Map<String, Object> claims, String subject) {
    
   return Jwts.builder() //
        .setSubject(subject) //
        .setIssuedAt(new Date(System.currentTimeMillis())) //
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) //
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY) //
        .compact();
}
    

  /**
   * validate token
   * 
   * @param token
   * @param username
   * @return
   */
  public Boolean validateToken(String token, String username) {
    String tokenUsername = getUsernameFromToken(token);
    return (tokenUsername.equals(username) && !isTokenExpired(token));
  }

}
