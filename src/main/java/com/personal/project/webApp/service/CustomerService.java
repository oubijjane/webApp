package com.personal.project.webApp.service;

import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(int id);

    void save(Customer customer);

    void deleteById(int id);


    List<OrderList> getOrders(int id);

    List<Customer> findByEmail(String email);


   Customer FindCustomerByEmail(String email);
}
