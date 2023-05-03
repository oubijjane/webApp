package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Product;

import java.util.List;

public interface CustomerDAO {

    List<Customer> findAll();

    Customer findById(int id);

    void save(Customer customer);

    void deleteById(int id);

    List<Product> getProducts(int id);
}
