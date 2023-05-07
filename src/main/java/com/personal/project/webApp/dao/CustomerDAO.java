package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDAO extends JpaRepository<Customer, Integer>{

    List<Customer> findByEmail(String email);
    //List<Product> getProducts(int id);

    //void addToCart(int id, Product product);
}
