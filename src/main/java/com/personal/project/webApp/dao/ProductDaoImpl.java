package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDAO {

    private EntityManager entityManager;

    @Autowired
    public ProductDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> products = entityManager.createQuery("FROM Product", Product.class);
        return products.getResultList();
    }

    @Override
    public Product findById(int id) {
        System.out.println(entityManager.find(Product.class, id));
        return entityManager.find(Product.class, id);
    }

    @Override
    public void save(Product product) {
        Product theProduct = entityManager.merge(product);
    }

    @Override
    public void deleteById(int id) {
    Product product = findById(id);
    entityManager.remove(product);
    }
}
