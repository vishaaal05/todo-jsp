package com.in28minutes.springboot.myfirstwebapp.security;


import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
			
	};

//	InMemoryUserDetailsManager
//	InMemoryUserDetailsManager(UserDetails... users)
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		
		UserDetails userDetails1 = createNewUser("vishal", "admin");
		UserDetails userDetails2 = createNewUser("manoj", "password");	
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder
		= input -> passwordEncoder().encode(input);
		
		
		UserDetails userDetails = User.builder()
				.passwordEncoder(passwordEncoder)
				.username(username)
				.password(password)
				.roles("USER", "ADMIN")
				.build();
		return userDetails;
	} 
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//all url are protected
	//login form is shown for unauthorized request
	//CSRF disable
	//by default, h2 database uses frames in html but 
	//spring doesn't allow it show we need to deal with the frames also 
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth
//				.requestMatchers(PUBLIC_URLS).permitAll()
//				.requestMatchers(HttpMethod.GET).permitAll()
				.anyRequest()
				.authenticated());
		http.formLogin(withDefaults());
		
//		http.csrf().disable();    -----> deprected 
//		http.headers().frameOptions().disable();   -----> deprected
		
		http.csrf(csrf -> csrf.disable());
		http.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions
					.sameOrigin()));
		
		return http.build();
	}
	
}
