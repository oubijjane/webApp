package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Product;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    @Transactional
    public Customer findById(int id) {
        Hibernate.initialize(customerDAO.findById(id));
        return customerDAO.findById(id);
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        customerDAO.deleteById(id);
    }

    @Override
    @Transactional
    public List<Product> getProducts(int id) {
        Hibernate.initialize(customerDAO.getProducts(id));
        return customerDAO.getProducts(id);
    }

    @Override
    @Transactional
    public void addToCart(int id, Product product) {
        Hibernate.initialize(product);
        customerDAO.addToCart(id, product);
    }
}
