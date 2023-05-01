package com.personal.project.webApp.service;

import com.personal.project.webApp.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int id);

    Product save(Product product);

    void deleteById(int id);
}
