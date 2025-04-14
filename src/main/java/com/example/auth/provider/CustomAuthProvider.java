package com.example.auth.provider;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.auth.entity.UserEntity;
import com.example.auth.repository.UserRepository;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		Optional<UserEntity> user = userRepository.findByLoginId(userName);
		if(user.isPresent()) {
			if(user.get().getPwd().equals(password)) {
				List<GrantedAuthority> roles = Arrays
			              .asList(new SimpleGrantedAuthority(user.get().getRole().getRoleName()));
				UserDetails userDetails = new User(userName, password, roles);
				return new UsernamePasswordAuthenticationToken(userDetails, password, roles);
			}
		}else {			
	            throw new BadCredentialsException("Authentication failed for " + userName);	       
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
