package com.tcs.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppSecurity{

         @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder )
	{
		UserDetails principal = User.withUsername("Ratan")
				.password(encoder.encode("Ratan@123"))
				.roles("PRINCIPAL")
				.build();
		UserDetails teacher = User.withUsername("Raju")
				.password(encoder.encode("Raju@123"))
				.roles("TEACHER")
				.build();
		  
		  return new InMemoryUserDetailsManager(principal ,teacher);
		 
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf().disable()
	        .authorizeHttpRequests()
	        .requestMatchers("/").permitAll()
	        .and()
	        .authorizeHttpRequests()
	        .requestMatchers("/**")
	        .authenticated()
	        .and()
	        .logout()
	        .logoutSuccessUrl("/") // Redirect to index.html after logout
	        .permitAll()
	        .and()
	        .formLogin()
	        .and()
	        .build();
	}


}