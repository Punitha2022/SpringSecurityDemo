package com.springsecuritydemo.SpringSecurityExample.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import com.springsecuritydemo.SpringSecurityExample.models.Customer;
import com.springsecuritydemo.SpringSecurityExample.models.ErrorClazz;
import com.springsecuritydemo.SpringSecurityExample.models.LoginCredentials;
import com.springsecuritydemo.SpringSecurityExample.service.CustomerRegistrationService;
import com.springsecuritydemo.SpringSecurityExample.service.CustomerUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@CrossOrigin("http://localhost:3000")
@RestController
public class CustomerController {
	@Autowired
	private CustomerRegistrationService customerReg;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private SecurityContextRepository securityContextRepository=
			new HttpSessionSecurityContextRepository();
	@PostMapping("/public/register")
	public ResponseEntity<?> register(@RequestBody Customer customer)
	{
		String password=customer.getPassword();
		String hashPwd=passwordEncoder.encode(password);
		customer.setPassword(hashPwd);
		customer.setRole("User");
	  customer=	customerReg.registerCustomer(customer);
	  if(customer.getId()>0)
		  return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	  else
		  return new ResponseEntity<String>("Unable to register",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping("/public/login")
	public ResponseEntity<?> login(@RequestBody LoginCredentials loginCredentials,
			HttpServletRequest request,
			HttpServletResponse response){
		Authentication authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword()));
		SecurityContext context=SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
	    securityContextRepository.saveContext(context, request, response);
		System.out.println(authentication.getPrincipal());
		if(authentication.isAuthenticated())
			return new ResponseEntity<Object>(authentication.getPrincipal(),HttpStatus.OK);
		else
			return new ResponseEntity<String>("Invalid user",HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/logoutsuccess")
	public ResponseEntity<?> logout(HttpServletRequest request){
		System.out.println("Inside logout.......");
		HttpSession session=request.getSession();
		session.invalidate();
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.clearContext();
		authentication.setAuthenticated(false);
		return new ResponseEntity<String>("Loggedout Successfully",HttpStatus.OK);
	}
}






