package com.springsecuritydemo.SpringSecurityExample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springsecuritydemo.SpringSecurityExample.dao.CustomerDao;
import com.springsecuritydemo.SpringSecurityExample.models.Customer;
import com.springsecuritydemo.SpringSecurityExample.models.LoginCredentials;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerRegistrationService {
	@Autowired
private CustomerDao customerDao;
	
	public Customer registerCustomer(Customer customer) {
		return customerDao.save(customer);
	}
	public Customer login(LoginCredentials lc) {
		return customerDao.findByEmail(lc.getEmail());
	}
}
