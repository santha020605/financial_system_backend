package com.zorvyn.finance.configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtility {

	private final String secret_key = "santha@8270320054$kumarsecretkeyforJwtauthentication";

	private Key getSignKey() {
		return Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String email, String role) {
		return Jwts.builder().
				setSubject(email)
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // For one Hour
				.signWith(getSignKey())
				.compact();
	}

	public String extractEmail(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public String extractRole(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get("role", String.class);
	}

}
