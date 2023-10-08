package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.dao.OrderListDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<Customer> result = customerDAO.findById(id);
        Customer customer = null;
        if (result.isPresent()) {
            customer = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find customer id - " + id);
        }
        return customer;
    }

    @Override
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public void deleteById(int id) {
        customerDAO.deleteById(id);
    }



    @Override
    public Customer findByEmail(String email) {
        return customerDAO.findByEmail(email);
    }



    @Override
    public Customer FindCustomerByEmail(String email) {
        return customerDAO.findByEmail(email);
    }

    @Override
    @Transactional
    public List<OrderList> getOrders(int id) {
        List<OrderList> orderLists = findById(id).getOrders();
        Hibernate.initialize(orderLists);
        return orderLists;
    }

}
