package com.springsecuritydemo.SpringSecurityExample.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecuritydemo.SpringSecurityExample.models.Customer;
@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
     Customer findByEmail(String email);
}
