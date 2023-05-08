package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.ProductDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Product;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    @Transactional
    public Product findById(int id) {
        Optional<Product> result = productDAO.findById(id);
        Product product = null;
        if (result.isPresent()) {
            product = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find product id - " + id);
        }
        return product;
    }

    @Override
    public Product save(Product product) {
        productDAO.save(product);
        return product;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        productDAO.deleteById(id);
    }
}
