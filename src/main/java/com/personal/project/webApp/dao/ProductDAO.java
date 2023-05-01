package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.Product;

import java.util.List;

public interface ProductDAO {

    List<Product> findAll();

    Product findById(int id);

    void save(Product product);

    void deleteById(int id);


}
