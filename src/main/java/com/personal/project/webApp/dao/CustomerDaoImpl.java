package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDAO{

    private EntityManager entityManager;

    @Autowired
    public CustomerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> customers = entityManager.createQuery("FROM Customer", Customer.class);

        return customers.getResultList();
    }

    @Override

    public Customer findById(int id) {
        return entityManager.find(Customer.class, id);
    }

    @Override

    public void save(Customer customer) {
        Customer theCustomer = entityManager.merge(customer);

    }

    @Override
    public void deleteById(int id) {
        Customer customer = findById(id);
        entityManager.remove(customer);
    }

    @Override

    public List<Product> getProducts(int id) {
        Customer customer = findById(id);
        return customer.getProducts();
    }
}
