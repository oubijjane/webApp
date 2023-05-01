package com.personal.project.webApp.controller;

import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/temps")
public class ProductController {

    private ProductService productService;
    private StorageService storageService;

    @Autowired
    public ProductController(ProductService productService, StorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public String listProducts(Model theModel) {
        List<Product> products = productService.findAll();

        theModel.addAttribute("products", products);

        return "products/list-products";
    }
    @GetMapping("/cart")
    public String cart(@RequestParam(value = "id") int id, Model theModel) {
        Product product = productService.findById(id);
        theModel.addAttribute("product", product);
        return "products/product";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("product") Product product,@RequestParam(value = "file", required = false) MultipartFile file) {
        if(file.isEmpty()){
            productService.save(product);
            return "redirect:/temps/list";
        }

        String picture = product.getId() + "-" +product.getProductName() + ".jpg";
        storageService.store(file, picture);
        product.setPictureLocation("/images/" + picture);
        productService.save(product);
        return "redirect:/temps/list";
    }
    @PostMapping("/add-to-cart")
    public String addToCart(@ModelAttribute("product") Product product, @RequestParam(value = "quantity") int num){
        int quantity = product.getQuantity() - num;
        product.setQuantity(quantity);
        productService.save(product);
        return "redirect:/temps/list";
    }
}
