package com.personal.project.webApp.controller;

import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.service.CustomerService;
import com.personal.project.webApp.service.OrderListService;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    private OrderListService orderListService;

    private ProductService productService;

    private CustomerService customerService;

    private StorageService storageService;

    @Autowired
    public ProductController(
            ProductService productService,
            StorageService storageService,
            CustomerService customerService,
            OrderListService orderListService) {
        this.productService = productService;
        this.storageService = storageService;
        this.customerService = customerService;
        this.orderListService = orderListService;
    }

    @GetMapping("/list")
    public String listProducts(Model theModel) {
        List<Product> products = productService.findAll();

        theModel.addAttribute("products", products);

        return "products/list-products";
    }
    @GetMapping("/cart")
    public String cart(@RequestParam(value = "id") int id, Model theModel, Model model) {
        Product product = productService.findById(id);
        theModel.addAttribute("product", product);
        return "products/product";
    }
    @GetMapping("/order")
    public String order(Model theModel, Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int theId = customerService.FindCustomerByEmail(user.getUsername()).getId();
        List<OrderList> ordersList = customerService.getOrders(theId);
        double sum = 0;
        for(OrderList orderList : ordersList){
            sum = sum +(orderList.getQuantity() * orderList.getProduct().getPrice());
        }
        sum = (double) (Math.round(sum*100.0)/100.0);
        model.addAttribute("sum", sum);
        theModel.addAttribute("ordersList", ordersList);
        return "products/order-list";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("product") Product product,@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam int quant) {
        System.out.println("/->>>>>>>" + quant);
        int quantity = product.getQuantity() - quant;
        product.setQuantity(quantity);
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
    public String addToCart(@ModelAttribute("product") Product product, @RequestParam int quant){
        int quantity = product.getQuantity() - quant;
        product.setQuantity(quantity);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int theId = customerService.FindCustomerByEmail(user.getUsername()).getId();
        OrderList orderList = new OrderList(quant,customerService.FindCustomerByEmail(user.getUsername()), product);
        customerService.addToCart(theId, product);
        productService.save(product);
        orderListService.save(orderList);
        return "redirect:/temps/list";
    }

}
