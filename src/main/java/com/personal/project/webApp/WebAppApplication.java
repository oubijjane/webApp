package com.personal.project.webApp;


import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.dao.ProductDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.service.CustomerService;
import com.personal.project.webApp.service.OrderListService;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.storage.StorageProperties;
import com.personal.project.webApp.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CustomerService customerDAO, ProductService productDAO, OrderListService orderListService) {
		return runner -> {

			//saveCustomer(customerDAO);
			//addProductstoCart(customerDAO,productDAO);
			//findByEmail(customerDAO);
			//AddProduct(productDAO);
			//addOrder(customerDAO, productDAO, orderListService);

		};
	}

	private void addOrder(CustomerService customerDAO, ProductService productDAO, OrderListService orderListService) {
		Customer customer = customerDAO.findById(1);
		System.out.println(customer);
		Product product = productDAO.findById(4);
		System.out.println(product);
		OrderList orderList = new OrderList(4,customer, product);

		orderListService.save(orderList);
	}

	private void AddProduct(ProductService productDAO) {
		Product product = new Product("milka", 2.2F, "/images/milka.jpg", "100g per container", 10);

		productDAO.save(product);
	}

	private void findByEmail(CustomerService customerDAO) {


		List<Product> productList = customerDAO.getProducts(1);

		System.out.println(productList);
	}

	@Transactional
	private void addProductstoCart(CustomerService customerDAO, ProductService productDAO) {
		List<Product> products = productDAO.findAll();
		System.out.println("1///");
		Customer customer = customerDAO.findById(1);
		System.out.println("2///");
		Product product = productDAO.findById(7);
		System.out.println("3///");
		customerDAO.addToCart(1,product);

	}

	private void saveCustomer(CustomerService customerDAO) {
		Customer customer = new Customer("John", "Dao", "casablanca", "John@gmail.com", "john");

		customerDAO.save(customer);
	}


}
