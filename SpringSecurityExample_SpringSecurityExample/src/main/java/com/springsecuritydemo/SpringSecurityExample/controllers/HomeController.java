package com.springsecuritydemo.SpringSecurityExample.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController {
@GetMapping("/public/home")
public ResponseEntity<?> homeController(){
	return new ResponseEntity<String>("Hello ",HttpStatus.OK);
}
@GetMapping("/public/contactus")
public ResponseEntity<?> contactUs(){
	return new ResponseEntity<String>("Contact Us for further details...",HttpStatus.OK);
}
@GetMapping("/addproduct")
public ResponseEntity<?> addProduct(){
	return new ResponseEntity<String>("Added successfully..",HttpStatus.OK);
}
}
