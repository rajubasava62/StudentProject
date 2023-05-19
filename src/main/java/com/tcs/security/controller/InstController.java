package com.tcs.security.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tcs.security.entity.Student;
import com.tcs.security.service.InstService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class InstController {
	
	@Autowired
	InstService instService;
	
	
	@RequestMapping("/")
	public String homePage()
	{
		return "index";
	}	
	
	
	@RequestMapping("/teachers")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public String teacherHomePage(Model model)
	{
		List<Student> students = instService.getAllStudents();
		model.addAttribute("students",students);
		return "teacher";
	}
	@RequestMapping("/principal")
	@PreAuthorize("hasRole('ROLE_PRINCIPAL')")
	public String principalHomePage(Model model)
	{
	List<Student> students = instService.getAllStudents();
	model.addAttribute("students",students);
	return "principalform";
	}
	
	// In a controller class or a logout endpoint
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
	    // Clear session or authentication token
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    
	    return "redirect:/";
	}
	
	@RequestMapping("/new")
	public String newStudent(Model model)
	{
		model.addAttribute("student",new Student());
		return "new_Student";		
	}
	
	@RequestMapping("/save")
	public String addProduct(Student student)
	{
		instService.addStudent(student);
		return "redirect:/principal";
	}
	@RequestMapping("/delete/{id}")
	public String deleteStudent(@PathVariable Long id)
	{
		instService.deleteProductById(id);
		return "redirect:/principal";
	}
	@RequestMapping("/edit/{id}")
	public String editStudent(@PathVariable Long id,Model model)
	{
		Student student = instService.get(id);
		model.addAttribute("student", student);
		return "edit_form";
	}
	
	@PostMapping("/admin-login")
	public String adminLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
	    boolean loginSuccess = instService.loginPrincipal(username, password);
	    
	    if (loginSuccess) {
	        return "redirect:/admin-dashboard";
	    } else {
	        return "redirect:/admin-login?error";
	    }
	}

	@PostMapping("/user-login")
	public String userLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
	    boolean loginSuccess = instService.loginTeacher(username, password);
	    
	    if (loginSuccess) {
	        return "redirect:/user-dashboard";
	    } else {
	        return "redirect:/user-login?error";
	    }

	}
}
