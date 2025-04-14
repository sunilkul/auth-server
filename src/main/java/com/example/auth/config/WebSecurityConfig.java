package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.example.auth.filter.CustomAuthenticationFilter;
import com.example.auth.provider.CustomAuthProvider;
import com.example.auth.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig{
	
	// refer url for more detials
	// https://riteshpanigrahi.com/spring-security-architecture-and-internal-workflow
	
	@Autowired
	CustomAuthProvider customAuthProvider;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.cors(AbstractHttpConfigurer::disable)
       .csrf(AbstractHttpConfigurer::disable)
		//.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository()))
        .securityMatcher("/api/auth/**","/cricket/**")
        .authorizeHttpRequests(request -> request
        		.requestMatchers("/cricket/**").permitAll()
        		.requestMatchers("/api/auth/**").permitAll()
        		.anyRequest().authenticated())
        .sessionManagement(sessionConfig -> sessionConfig
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(new CustomAuthenticationFilter("/api/auth/login", authenticationManager(), objectMapper, jwtUtil), UsernamePasswordAuthenticationFilter.class);				
		
		return http.build();
	}
	
	@Bean	
	public AuthenticationManager authenticationManager() {
		return authentication -> customAuthProvider.authenticate(authentication);
	}
	
	@Bean
	  public AuthenticationManager authenticationManagerNew(HttpSecurity http) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authenticationManagerBuilder.authenticationProvider(customAuthProvider);
	    return authenticationManagerBuilder.build();
	  }
	
	/*@Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }*/
	
	@Bean 
	public CsrfTokenRepository csrfTokenRepository() {
		CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");	
		repository.setCookieName("X-XSRF-TOKEN");
		return repository;
	}
	

}
