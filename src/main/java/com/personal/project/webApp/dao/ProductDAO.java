package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    
}
