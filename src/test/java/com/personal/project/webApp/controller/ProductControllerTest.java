package com.personal.project.webApp.controller;

import com.personal.project.webApp.EmailValid;
import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.security.SecurityConfigs;
import com.personal.project.webApp.service.CustomerService;
import com.personal.project.webApp.service.OrderListService;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.service.RolesServiceImpl;
import com.personal.project.webApp.storage.StorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ProductController.class)
@Import({ProductController.class, SecurityConfigs.class})
@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = SecurityConfigs.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerDAO customerDAO;

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

    private Customer customer;

    @BeforeEach
    void setUp() {

    }

    @Test
    void initBinder() {
    }

    @Test
    //@WithMockUser(username = "user2@gmail.com", roles = "CUSTOMER")
    void listProducts() throws Exception {
        this.mockMvc.perform(get("/temps/list")).andExpect(status().isOk())
                .andExpect(view().name("products/list-products"));
        verify(productService).findAll();

    }

    @Test
    void customers_IsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/temps/accounts")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user2@gmail.com", roles = "ADMIN")
    void customers_Authorized() throws Exception {

        this.mockMvc.perform(get("/temps/accounts")).andExpect(status().isOk())
                .andExpect(view().name("products/list-customers"));
        verify(customerService).findAll();

    }
    @Test
    @WithMockUser(username = "user2@gmail.com", roles = "CUSTOMER")
    void customers_Forbidden() throws Exception {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        this.mockMvc.perform(get("/temps/accounts")).andExpect(status().isForbidden());
    }

    @Test
    void cart() throws Exception {
        when(productService.findById(1)).thenReturn(new Product());
        this.mockMvc.perform(get("/temps/cart?id=1")).andExpect(status().isOk())
                .andExpect(view().name("products/product"));
        verify(productService).findById(1);


    }

    @Test
    void order_Unauthorized() throws Exception{
        this.mockMvc.perform(get("/temps/shopping-list")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "CUSTOMER")
    void order_Authorized() throws Exception{
        when(customerService.FindCustomerByEmail("test@gmail.com")).thenReturn(new Customer());
        this.mockMvc.perform(get("/temps/shopping-list")).andExpect(status().isOk())
                .andExpect(view().name("products/shopping-list"));
        verify(customerService).FindCustomerByEmail("test@gmail.com");
        verify(customerService).getOrders(0);
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "CUSTOMER")
    void addProduct_Forbidden () throws Exception {
        this.mockMvc.perform(get("/temps/add-product")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "ADMIN")
    void addProduct_Authorized () throws Exception {
        this.mockMvc.perform(get("/temps/add-product")).andExpect(status().isOk())
                .andExpect(model().attribute("product", new Product()))
                .andExpect(view().name("products/add-form"));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "ADMIN")
    void update_Delete() throws Exception {
        Product product = new Product();

        this.mockMvc.perform(post("/temps/update").param("quant", "1").param("action", "delete").flashAttr("product", product).with(csrf()))
                .andExpect(redirectedUrl("/temps/list")).andExpect(status().isFound());

        verify(storageService).delete(anyString());
        verify(productService).deleteById(product.getId());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "ADMIN")
    void update_Update() throws Exception {
        Product product = new Product();
        MockMultipartFile file = new MockMultipartFile("file", "hello.jpg", MediaType.IMAGE_JPEG_VALUE, "hello word!".getBytes());

        this.mockMvc.perform(multipart("/temps/update").file(file).param("quant", "1").param("action", "update").flashAttr("product", product).with(csrf()))
                .andExpect(redirectedUrl("/temps/list")).andExpect(status().isFound());

        assertThat(product.getPictureLocation()).isEqualTo("/images/0-null.jpg");
        verify(storageService).store(file, "0-null.jpg");
        verify(productService).save(product);
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "ADMIN")
    void update_Update_Empty_File() throws Exception {
        Product product = new Product();
        MockMultipartFile file = new MockMultipartFile("file", "hello.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        this.mockMvc.perform(multipart("/temps/update").file(file).param("quant", "1").param("action", "update").flashAttr("product", product).with(csrf()))
                .andExpect(redirectedUrl("/temps/list")).andExpect(status().isFound());

        assertThat(product.getPictureLocation()).isEqualTo(null);
        verify(productService).save(product);
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "ADMIN")
    void add() throws Exception {
        Product product = new Product();
        MockMultipartFile file = new MockMultipartFile("file", "hello.jpg", MediaType.IMAGE_JPEG_VALUE, "hello word!".getBytes());

        this.mockMvc.perform(multipart("/temps/add").file(file).param("quant", "1").flashAttr("product", product).with(csrf()))
                .andExpect(redirectedUrl("/temps/list")).andExpect(status().isFound());

        assertThat(product.getPictureLocation()).isEqualTo("/images/0-null.jpg");
        verify(storageService).store(file, "0-null.jpg");
        verify(productService, times(2)).save(product);

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