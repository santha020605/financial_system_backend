package com.zorvyn.finance.configuration;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtility jwtUtility;

	public JwtFilter(JwtUtility jwtUtility) {
		this.jwtUtility = jwtUtility;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		try {
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);

				String email = jwtUtility.extractEmail(token);
				String role = jwtUtility.extractRole(token);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null,
						Collections.emptyList());

				SecurityContextHolder.getContext().setAuthentication(auth);

				request.setAttribute("email", email);
				request.setAttribute("role", role);

			}
		} catch (Exception e) {
			throw new RuntimeException("Invalid Token");
		}

		filterChain.doFilter(request, response);
	}

}
