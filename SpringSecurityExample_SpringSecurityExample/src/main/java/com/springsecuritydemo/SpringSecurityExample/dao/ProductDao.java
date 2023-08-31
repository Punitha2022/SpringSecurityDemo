package com.springsecuritydemo.SpringSecurityExample.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springsecuritydemo.SpringSecurityExample.models.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	Product findByName(String name);
	@Query("select p.price from Product p where p.name=?1")
	Optional<Double> findPriceByName(String name);

}
