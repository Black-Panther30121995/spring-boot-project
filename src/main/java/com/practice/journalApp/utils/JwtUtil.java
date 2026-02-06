package com.practice.journalApp.utils;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY ="MySuperSecretJwtKeyThatIsAtLeast32Chars!";


	
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)  
                   .getBody();             
    }

	public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
	
	 private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	
	 private String createToken(Map<String, Object> claims, String subject) {
		    return Jwts.builder()
		               .setClaims(claims)
		               .setSubject(subject)
		               .setIssuedAt(new Date(System.currentTimeMillis()))
		               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
		               .signWith(getSigningKey())
		               .compact();
		}

	
	public Boolean validateToken(String token) {
	        return !isTokenExpired(token);
	    }
	 



}
