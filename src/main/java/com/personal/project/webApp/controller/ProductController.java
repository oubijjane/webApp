package com.personal.project.webApp.controller;

import com.personal.project.webApp.EmailValid;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.entity.Roles;
import com.personal.project.webApp.service.CustomerService;
import com.personal.project.webApp.service.OrderListService;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.service.RolesServiceImpl;
import com.personal.project.webApp.storage.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/temps")
public class ProductController {


    private EmailValid emailValid;
    private OrderListService orderListService;

    private ProductService productService;

    private CustomerService customerService;

    private StorageService storageService;

    private PasswordEncoder passwordEncoder;

    private RolesServiceImpl rolesService;


    @Autowired
    public ProductController(
            ProductService productService,
            StorageService storageService,
            CustomerService customerService,
            OrderListService orderListService,
            PasswordEncoder passwordEncoder,
            EmailValid emailValid,
            RolesServiceImpl rolesService) {
        this.productService = productService;
        this.storageService = storageService;
        this.customerService = customerService;
        this.orderListService = orderListService;
        this.passwordEncoder = passwordEncoder;
        this.emailValid = emailValid;
        this.rolesService = rolesService;
    }
    @InitBinder(value="customer")
    protected void initBunder(WebDataBinder binder){
        binder.setValidator(emailValid);
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
    @GetMapping("/shopping-list")
    public String order(Model theModel) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int theId = customerService.FindCustomerByEmail(user.getUsername()).getId();
        List<OrderList> ordersList = customerService.getOrders(theId);
        theModel.addAttribute("ordersList", ordersList);
        return "products/shopping-list";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "products/add-form";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("product") Product product,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @RequestParam int quant,
                         @RequestParam("action") String action ) {
        if(action.equals("delete")) {
            productService.deleteById(product.getId());


        }if(action.equals("update")) {
            System.out.println("/->>>>>>>" + quant);
            int quantity = product.getQuantity() - quant;
            product.setQuantity(quantity);
            if (file.isEmpty()) {
                productService.save(product);
                return "redirect:/temps/list";
            }

            String picture = product.getId() + "-" + product.getProductName() + ".jpg";
            storageService.store(file, picture);
            product.setPictureLocation("/images/" + picture);
            productService.save(product);
        }
        return "redirect:/temps/list";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute("product") Product product) {

        productService.save(product);
        return "redirect:/temps/list";
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productService.deleteById(product.getId());
        return "redirect:/temps/list";
    }
    @PostMapping("/add-to-cart")
    public String addToCart(@ModelAttribute("product") Product product, @RequestParam int quant, @RequestParam("action") String action){

            System.out.println("kkkkkkkkkkkkkk>>>>>>>>>>>>>>");
            int quantity = product.getQuantity() - quant;
            product.setQuantity(quantity);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int theId = customerService.FindCustomerByEmail(user.getUsername()).getId();
            OrderList orderList = new OrderList(quant, customerService.FindCustomerByEmail(user.getUsername()), product);
            productService.save(product);
            orderListService.save(orderList);

        return "redirect:/temps/list";
    }
    @GetMapping("/add-account")
    public String addAccount(Model model) {
        Customer customer = new Customer();



        model.addAttribute("customer", customer);
        return "products/add-account";
    }

    @PostMapping("/add-account")
    public String addCustomer(@ModelAttribute("customer") @Validated Customer customer, BindingResult result, @RequestParam String password) {


        if(result.hasErrors()){
            return "products/add-account";
        }/*
        for(Customer tempCustomer : customerService.findAll()){
            if(tempCustomer.getEmail().equals(customer.getEmail())){
                message = "email already exist";
                this.message = message;
                System.out.println(message);
                return "products/add-account";
            }
        }*/
        customer.setPassword(passwordEncoder.encode(password));
        customerService.save(customer);
        Roles role = new Roles(customer, "ROLE_CUSTOMER");
        rolesService.save(role);
        return "redirect:/temps/list";
    }

}
