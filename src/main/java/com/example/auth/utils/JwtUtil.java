package com.example.auth.utils;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.auth.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	
	private static final int EXPIRES_IN_MS = 180000;
	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final String AUTHORITIES = "authorities";
	
	public String generateToken(Authentication authentication) {
		
		String authorities = authentication.getAuthorities()
				.stream()
				.map(x -> x.getAuthority())
				.collect(Collectors.joining());
		
		return Jwts.builder().setSubject(authentication.getName())
		.claim(AUTHORITIES, authorities)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN_MS))
		.signWith(SECRET_KEY, SignatureAlgorithm.HS256)		
		.compact();	
	}
	
	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T getClaimFromToken(String token,Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaims(token);
		return claimResolver.apply(claims);				
	}
	
	public String getUserName(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getClaimFromToken(token, Claims::getExpiration);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserEntity user) {
		final String userName = getUserName(token);
		return (userName.equals(user.getLoginId()) && !isTokenExpired(token));
		
	}
	
	public UsernamePasswordAuthenticationToken getAuthenticationToken(String token, UserEntity user) {		
		final Claims claims = getAllClaims(token);
		final List<GrantedAuthority> authorities = Arrays.asList(claims.get(AUTHORITIES).toString().split(","))
				.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new UsernamePasswordAuthenticationToken(user,"",authorities);				
		
	}
		
}
	

