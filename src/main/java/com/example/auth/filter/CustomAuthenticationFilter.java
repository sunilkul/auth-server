package com.example.auth.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.auth.model.UserLogin;
import com.example.auth.utils.JwtUtil;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private ObjectMapper objectMapper;
	
	private AuthenticationManager authManager;
	
	private JwtUtil jwtUtil;
	
	@Autowired
	public CustomAuthenticationFilter(String loginUrl, AuthenticationManager authmanager, ObjectMapper objectMapper,
			JwtUtil jwtUtil) {
		super(loginUrl);
		this.objectMapper = objectMapper;
		this.authManager = authmanager;
		this.jwtUtil = jwtUtil;
	}
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws StreamReadException, DatabindException, IOException {

		UserLogin userLogin = this.objectMapper.readValue(request.getReader(), UserLogin.class);

		System.out.println(userLogin);

		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userLogin.getUserName(),
				userLogin.getPassword());

		System.out.println("call to the registerd auth provider");
		return authManager.authenticate(userToken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String jwtToken = this.jwtUtil.generateToken(authResult);
		Cookie cookie = new Cookie("accessToken", jwtToken);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		//set domain in case of https and certificate
		cookie.setDomain("/");
		response.addCookie(cookie);
		//response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

}
