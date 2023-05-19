package com.tcs.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.security.entity.Student;
import com.tcs.security.repository.InstRepository;

@Service
public class InstService {
	
	@Autowired
	InstRepository repository;
	

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsService userDetailsService;

	public List<Student> getAllStudents() {
		
		return repository.findAll();
	}

	public void addStudent(Student student) {
		repository.save(student);
		
	}

	public void deleteProductById(Long id) {
		repository.deleteById(id);
		
	}

	public Student get(Long id) {
		return repository.findById(id).get();
	}

	public boolean loginPrincipal(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword()) &&
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRINCIPAL"))) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true; // Successful login
        } else {
            return false; // Invalid credentials
        }
    }
	
	 public boolean loginTeacher(String username, String password) {
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	        
	        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword()) &&
	                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
	            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
	                    userDetails.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            return true; // Successful login
	        } else {
	            return false; // Invalid credentials
	        }
	    }

}
