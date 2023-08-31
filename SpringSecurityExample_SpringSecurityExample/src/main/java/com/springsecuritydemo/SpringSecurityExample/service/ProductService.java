package com.springsecuritydemo.SpringSecurityExample.service;

import java.util.List;
import java.util.Optional;

import com.springsecuritydemo.SpringSecurityExample.models.Product;



public interface ProductService {
List<Product> getAllProducts();

Product getProduct(int id);

Product getProductByName(String name);

void saveProduct(Product product);

void deleteProduct(int id);

void updateProduct(Product product);
double findPriceByName(String name);
}
