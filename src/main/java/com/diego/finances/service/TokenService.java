package com.diego.finances.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.diego.finances.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private String expiration;
	@Value("${jwt.secret}")
	private String secret;
	
	public String getToken(Authentication auth) {
		var user = (User) auth.getPrincipal();
		var today = new Date();
		var expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("Finances")
				.setSubject(user.getId().toString())
				.setIssuedAt(today)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();			
		return Long.parseLong(claims.getSubject());
	}
}