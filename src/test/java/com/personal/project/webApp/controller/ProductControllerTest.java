package com.personal.project.webApp.controller;

import com.personal.project.webApp.EmailValid;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.security.SecurityConfigs;
import com.personal.project.webApp.service.CustomerService;
import com.personal.project.webApp.service.OrderListService;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.service.RolesServiceImpl;
import com.personal.project.webApp.storage.StorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(controllers = ProductController.class)
@Import({ProductController.class, SecurityConfigs.class})
//@ContextConfiguration(classes = SecurityConfigs.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private EmailValid emailValid;
    @MockBean
    private OrderListService orderListService;
    @MockBean
    private ProductService productService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private StorageService storageService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private RolesServiceImpl rolesService;

    @AfterEach
    void tearDown() {
    }

    @Test
    void initBinder() {
    }

    @Test
    //@WithMockUser(username = "user2@gmail.com", roles = "CUSTOMER")
    void listProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(productService.findAll()).thenReturn(products);

        this.mockMvc.perform(get("/temps/list")).andExpect(status().isOk())
                .andExpect(view().name("products/list-products"));
    }

    @Test
    void customers_IsUnauthorized() throws Exception {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        this.mockMvc.perform(get("/temps/accounts")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user2@gmail.com", roles = "ADMIN")
    void customers_Authorized() throws Exception {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        this.mockMvc.perform(get("/temps/accounts")).andExpect(status().isOk());
    }

    @Test
    void cart() {
    }

    @Test
    void order() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void update() {
    }

    @Test
    void add() {
    }

    @Test
    void addToCart() {
    }

    @Test
    void addAccount() {
    }

    @Test
    void addCustomer() {
    }

    @Test
    void addAuthority() {
    }
}