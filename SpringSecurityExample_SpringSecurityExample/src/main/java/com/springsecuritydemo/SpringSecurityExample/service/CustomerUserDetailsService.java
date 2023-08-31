package com.springsecuritydemo.SpringSecurityExample.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecuritydemo.SpringSecurityExample.dao.CustomerDao;
import com.springsecuritydemo.SpringSecurityExample.models.Customer;

import jakarta.transaction.Transactional;
@Service
public class CustomerUserDetailsService implements UserDetailsService 
{
    @Autowired
	private CustomerDao customerDao;
    
   
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	    Customer customer=customerDao.findByEmail(username);
	    if(customer==null)
	    	throw new UsernameNotFoundException("User details not found");
	    else
	    {
	    	List<GrantedAuthority> authorities=new LinkedList<>();
	    	authorities.add(new SimpleGrantedAuthority(customer.getRole()));
	    	return new User(customer.getEmail(),customer.getPassword(), authorities);
	    }
	    
	}
	

}
