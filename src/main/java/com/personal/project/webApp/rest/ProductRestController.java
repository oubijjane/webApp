package com.personal.project.webApp.rest;

import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product findById(@PathVariable int id) {
        Product product = productService.findById(id);
        if(product == null) {
            throw new RuntimeException("Product id not found: " + id);
        }
        return product;
    }
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product theProduct) {
        theProduct.setId(0);

        return productService.save(theProduct);
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product theProduct) {
        return productService.save(theProduct);
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteById(id);

        return "deleting product with id of: " + id;
    }
}
